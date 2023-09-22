package com.example.test_ufanet.data.event;

import com.example.test_ufanet.data.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_event")
public class EventStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderId;
    private Integer clientId;
    private Integer employeeId;
    private LocalDateTime issueDateTime;
    private Integer productId;
    private Float cost;
    private String description;
    private LocalDateTime creationDateTime;
    private String type;
}
