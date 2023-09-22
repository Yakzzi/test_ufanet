package com.example.test_ufanet.data.exception;

public class OrderCancelingException extends Throwable {

    public OrderCancelingException(String orderId) {
        super("Order [" + orderId + "] can not be cancelled");
    }
}
