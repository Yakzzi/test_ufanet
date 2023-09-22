package com.example.test_ufanet.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRegisterDto {
    String orderId;
    String clientId;
    String employeeId;
    String productId;
    String cost;
}
