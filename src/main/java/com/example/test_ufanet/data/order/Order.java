package com.example.test_ufanet.data.order;

import com.example.test_ufanet.data.event.EventStore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class Order {

    private Integer id;
    private Integer clientId;
    private Integer employeeId;
    private LocalDateTime dateIssue;
    private Integer productId;
    private Float productCost;
    private String description;
    private LocalDateTime creationDate;
    private List<EventStore> eventStoreList;

}
