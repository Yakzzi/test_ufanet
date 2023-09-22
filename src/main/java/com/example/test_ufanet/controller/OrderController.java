package com.example.test_ufanet.controller;

import com.example.test_ufanet.data.dto.*;
import com.example.test_ufanet.data.event.EventStore;
import com.example.test_ufanet.data.exception.OrderNotRegisteredException;
import com.example.test_ufanet.data.response.GetOrderResponse;
import com.example.test_ufanet.data.response.OrderCanceledResponse;
import com.example.test_ufanet.data.response.OrderRegisterResponse;
import com.example.test_ufanet.data.response.OrderUpdatedResponse;
import com.example.test_ufanet.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping(value = "/register")
    public OrderRegisterResponse registerOrder(@RequestBody OrderRegisterDto dto) {
        return orderService.registerOrder(dto);
    }

    @PostMapping("/{orderId}/cancel")
    public OrderCanceledResponse cancelOrder(@PathVariable("orderId") String orderId,
                                                             @RequestBody OrderCancelDto dto) {
        return orderService.cancelOrder(orderId, dto);
    }

    @PostMapping("/{orderId}/in-work")
    public OrderUpdatedResponse orderInWork(@PathVariable("orderId") String orderId,
                                                            @RequestBody OrderInWorkDto dto) {
        return orderService.orderInWork(orderId, dto);
    }

    @PostMapping("/{orderId}/ready")
    public OrderUpdatedResponse orderReady(@PathVariable("orderId") String orderId,
                                            @RequestBody OrderReadyDto dto) {
        return orderService.orderReady(orderId, dto);
    }

    @PostMapping("/{orderId}/issue")
    public OrderUpdatedResponse issueOrder(@PathVariable("orderId") String orderId,
                                            @RequestBody OrderIssueDto dto) {
        return orderService.issueOrder(orderId, dto);
    }

    @GetMapping("/{orderId}")
    public GetOrderResponse listOfAllEventsById(@PathVariable String orderId) {
        return orderService.getAllEventsById(orderId);
    }
}
