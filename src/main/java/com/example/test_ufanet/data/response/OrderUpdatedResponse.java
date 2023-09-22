package com.example.test_ufanet.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderUpdatedResponse {
    String orderId;
    String employeeId;
    String message;
}
