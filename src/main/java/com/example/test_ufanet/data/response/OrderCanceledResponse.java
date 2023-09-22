package com.example.test_ufanet.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCanceledResponse {
    String orderId;
    String employeeId;
    String description;
    String message;
}
