package com.orders.api.resource.exception;

public class StateNotFoundException  extends RuntimeException {
    public StateNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
