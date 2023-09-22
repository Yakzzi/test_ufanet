package com.example.test_ufanet.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Data
public class OrderCancelDto {
    private String employeeId;
    private String description;
}
