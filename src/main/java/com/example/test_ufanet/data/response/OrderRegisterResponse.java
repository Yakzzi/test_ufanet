package com.example.test_ufanet.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRegisterResponse {
    String orderId;
    String employeeId;
    String message;
}
