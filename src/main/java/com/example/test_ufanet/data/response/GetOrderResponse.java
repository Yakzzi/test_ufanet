package com.example.test_ufanet.data.response;

import com.example.test_ufanet.data.dto.OrderEventsResponse;
import com.example.test_ufanet.data.event.EventStore;
import com.example.test_ufanet.data.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GetOrderResponse {
    String orderId;
    String clientId;
    String employeeId;
    LocalDateTime issueDateTime;
    String productId;
    String cost;
    String description;
    LocalDateTime creationDateTime;
    String type;
    List<OrderEventsResponse> eventsList;
}
