package com.example.test_ufanet.data.exception;

public class OrderNotRegisteredException extends Throwable {
    public OrderNotRegisteredException(String orderId) {
        super("Order [" + orderId + "] is not registered");
    }
}
