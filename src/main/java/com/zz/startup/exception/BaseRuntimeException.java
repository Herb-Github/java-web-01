package com.zz.startup.exception;

public class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseRuntimeException(){
        super();
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }
}
