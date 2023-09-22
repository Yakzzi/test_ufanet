package com.example.test_ufanet.data.dto;

import com.example.test_ufanet.data.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderEventsResponse {
    String type;
    LocalDateTime creationTime;
}
