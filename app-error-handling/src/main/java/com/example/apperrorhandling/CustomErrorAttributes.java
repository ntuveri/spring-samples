package com.example.apperrorhandling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	private static final String CODE_KEY = "code";
	private static final String ERRORS_KEY = "errors";
	private static final String KEYCLOAK_AUTH_ERROR_ATTRIBUTE = "org.keycloak.adapters.spi.AuthenticationError";

	private class CustomFieldError {
		private String field;
		private Object rejectedValue;
		private String defaultMessage;
		private String code;

		public CustomFieldError(FieldError fieldError) {
			this.field = fieldError.getField();
			this.rejectedValue = fieldError.getRejectedValue();
			this.defaultMessage = fieldError.getDefaultMessage();
			this.code = fieldError.getCode();
		}

		public String getField() {
			return field;
		}

		public Object getRejectedValue() {
			return rejectedValue;
		}

		public String getDefaultMessage() {
			return defaultMessage;
		}

		public String getCode() {
			return code;
		}
	}


	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options
			.including(ErrorAttributeOptions.Include.MESSAGE)
			.including(ErrorAttributeOptions.Include.BINDING_ERRORS));
		List<ObjectError> errors = (List<ObjectError>) errorAttributes.get(ERRORS_KEY);

		List<CustomFieldError> customFieldErrors = getCustomFieldErrors(errors);
		errorAttributes.remove(ERRORS_KEY);
		if(customFieldErrors != null && customFieldErrors.size() > 0) {
			errorAttributes.put(ERRORS_KEY, customFieldErrors);
		}

		String code = getCode(webRequest, errors);
		errorAttributes.put(CODE_KEY, code);

		return errorAttributes;
	}

	private String getCode(WebRequest webRequest, List<ObjectError> errors) {
		Throwable t = super.getError(webRequest);
		if(t == null) {
			return getKeycloakAuthErrorCode(webRequest);
		}

		// in case of binding / validation errors the InvalidRequest code is assigned
		String code;
		if(errors != null && errors.size() > 0) {
			code = "InvalidRequest";
			return code;
		}

		// otherwise the code is inferred from the exception type
		code = t.getClass().getSimpleName();
		if(code.endsWith("Exception")) {
			code = code.substring(0, code.length() - "Exception".length());
		}
		return code;
	}

	private List<CustomFieldError> getCustomFieldErrors(List<ObjectError> errorsWithInvalidKeys) {
		if(errorsWithInvalidKeys == null) {
			return null;
		}
		List<CustomFieldError> errors = new ArrayList<>(errorsWithInvalidKeys.size());
		for (ObjectError oe: errorsWithInvalidKeys) {
			if(oe instanceof FieldError) {
				FieldError fe = (FieldError) oe;
				errors.add(new CustomFieldError(fe));
			}
			// ObjectError are dropped
		}

		return errors;
	}

	private String getKeycloakAuthErrorCode(WebRequest webRequest) {
		// check auth errors handled by Keycloak adapter
		Object authError = webRequest
				.getAttribute(KEYCLOAK_AUTH_ERROR_ATTRIBUTE, WebRequest.SCOPE_REQUEST);

		return authError != null ? "AuthError" : null;
	}
}