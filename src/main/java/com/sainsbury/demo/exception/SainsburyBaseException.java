package com.sainsbury.demo.exception;

public class SainsburyBaseException extends Exception {

    private String errorCode;
    private String errorMessage;

    public SainsburyBaseException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }
}
