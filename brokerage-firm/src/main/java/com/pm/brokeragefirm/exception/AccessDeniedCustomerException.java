package com.pm.brokeragefirm.exception;

public class AccessDeniedCustomerException extends RuntimeException {
    public AccessDeniedCustomerException() {
        super("Access denied for this customer");
    }

}
