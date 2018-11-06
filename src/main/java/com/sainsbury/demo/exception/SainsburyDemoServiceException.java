package com.sainsbury.demo.exception;

public class SainsburyDemoServiceException extends SainsburyBaseException {

    public SainsburyDemoServiceException(String errorCode, String message) {
        super(errorCode, message);
    }
}
