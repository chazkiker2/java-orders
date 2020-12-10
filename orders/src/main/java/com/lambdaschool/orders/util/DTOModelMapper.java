package com.lambdaschool.orders.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;



/**
 * This class extends `RequestResponseBodyMethodProcessor` in order to avoid writing out the whole process of
 * converting reqs into classes. This means that this class takes in JSON body and transforms that into an instance
 * of a class. In our case, we tweak the base functionality of the clsas to populate an instance of a DTO instead.
 */
public class DTOModelMapper
		extends RequestResponseBodyMethodProcessor {
	/**
	 * Static instance of `ModelMapper`. This is used to map all DTOs into entities.
	 */
	private static final ModelMapper modelMapper = new ModelMapper();
	/**
	 * Inject entity manager in this class to be able to query the database for existing entities based on the `id`
	 * passed through DTOs.
	 */
	private              EntityManager entityManager;

	public DTOModelMapper(
			ObjectMapper objectMapper,
			EntityManager entityManager
	) {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
		this.entityManager = entityManager;
	}

	/**
	 * Without overrriding this method, the new class would be applied for @RequestBody parameters, just like the base
	 * class. Instead, we want the class to apply for @DTO annotations ONLY
	 * @param parameter MethodParameter
	 * @return true if parameter has DTO annotation; false if parameter does not have DTO annotation
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(DTO.class);
	}

	/**
	 * This beefy fella overrides `resolveArgument`, twaking it to embed the `ModelMapper` instance in the process and
	 * make it map DTOs into entities. Before mapping, we check if we are handling a new entity, or if we have to apply
	 * the changes proposed by the DTO in an existing entity.
	 * @param parameter
	 * @param mavContainer
	 * @param webRequest
	 * @param binderFactory
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory
	)
			throws
			Exception {
		Object dto = super.resolveArgument(
				parameter,
				mavContainer,
				webRequest,
				binderFactory
		);
		Object id = getEntityId(dto);
		if (id == null) {
			return modelMapper.map(
					dto,
					parameter.getParameterType()
			);
		} else {
			Object persistedObject = entityManager.find(
					parameter.getParameterType(),
					id
			);
			modelMapper.map(
					dto,
					persistedObject
			);
			return persistedObject;
		}
	}

	//	@Override
	//	protected <T> Object readWithMessageConverters(
	//			NativeWebRequest webRequest,
	//			MethodParameter parameter,
	//			Type paramType
	//	)
	//			throws
	//			IOException,
	//			HttpMediaTypeNotSupportedException,
	//			HttpMessageNotReadableException {
	//		return super.readWithMessageConverters(
	//				webRequest,
	//				parameter,
	//				paramType
	//		);
	//	}

	private Object getEntityId(@NotNull Object dto) {
		for (Field field : dto.getClass()
		                      .getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				try {
					field.setAccessible(true);
					return field.get(dto);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	@Override
	protected Object readWithMessageConverters(
			HttpInputMessage inputMesage,
			MethodParameter parameter,
			Type targetType
	)
			throws
			IOException,
			HttpMediaTypeNotSupportedException,
			HttpMessageNotReadableException {
		for (Annotation annotation : parameter.getParameterAnnotations()) {
			DTO dtoType = AnnotationUtils.getAnnotation(
					annotation,
					DTO.class
			);
			if (dtoType != null) {
				return super.readWithMessageConverters(
						inputMesage,
						parameter,
						dtoType.value()
				);
			}
		}
		throw new RuntimeException();
	}

	/**
	 * The base version of this method runs bean validation only if the param is marked with @Valid or @Validated. We
	 * change this behavior to apply bean validation on all DTOs.
	 * @param binder WebDataBinder
	 * @param parameter MethodParameter
	 */
	@Override
	protected void validateIfApplicable(
			WebDataBinder binder,
			MethodParameter parameter
	) {
		binder.validate();
		//		super.validateIfApplicable(
		//				binder,
		//				parameter
		//		);
	}

}
