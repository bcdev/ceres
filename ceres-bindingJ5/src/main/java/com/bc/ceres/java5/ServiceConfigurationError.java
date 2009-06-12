package com.bc.ceres.java5;

@SuppressWarnings("serial")
public class ServiceConfigurationError extends Error {

	public ServiceConfigurationError(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceConfigurationError(String message) {
		super(message);
	}

}
