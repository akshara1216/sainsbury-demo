package com.sainsbury.demo.exception;

public class SainsburyGroceryServiceException extends SainsburyBaseException {

    public SainsburyGroceryServiceException(String errorCode, String message) {
        super(errorCode, message);
    }
}
