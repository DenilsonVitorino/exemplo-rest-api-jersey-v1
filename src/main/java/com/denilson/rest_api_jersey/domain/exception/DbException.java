package com.denilson.rest_api_jersey.domain.exception;

public class DbException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }
}
