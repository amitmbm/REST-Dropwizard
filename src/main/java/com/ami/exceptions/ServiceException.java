package com.ami.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Application exception handling utility</p>
 */
public class ServiceException extends WebApplicationException {

	public ServiceException(Integer responseCode, ServiceError errorCode, String errorMessage) {
		super(Response.status(responseCode).entity(getError(errorCode, errorMessage)).build());
	}

	private static Map<String, String> getError(ServiceError errorCode, String errorMessage) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("CODE", errorCode.name());
		error.put("MESSAGE", errorMessage);
		return error;
	}

}
