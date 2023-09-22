package com.example.test_ufanet.service;
import com.example.test_ufanet.data.dto.*;
import com.example.test_ufanet.data.event.EventStore;
import com.example.test_ufanet.data.event.EventType;
import com.example.test_ufanet.data.response.GetOrderResponse;
import com.example.test_ufanet.data.response.OrderCanceledResponse;
import com.example.test_ufanet.data.response.OrderRegisterResponse;
import com.example.test_ufanet.data.response.OrderUpdatedResponse;
import com.example.test_ufanet.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private EventRepository eventRepository;

    public OrderRegisterResponse registerOrder(OrderRegisterDto dto) {
        Optional<EventStore> optionalEvent = Optional.ofNullable(eventRepository.findByOrderId(Integer.valueOf(dto.getOrderId())));

        if (optionalEvent.isEmpty()) {
            LocalDateTime issueDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(15));

            EventStore event = new EventStore();

            event.setOrderId(Integer.valueOf(dto.getOrderId()));
            event.setClientId(Integer.valueOf(dto.getClientId()));
            event.setEmployeeId(Integer.valueOf(dto.getEmployeeId()));
            event.setIssueDateTime(issueDateTime);
            event.setProductId(Integer.valueOf(dto.getProductId()));
            event.setCost(Float.valueOf(dto.getCost()));
            event.setCreationDateTime(LocalDateTime.now());
            event.setType(String.valueOf(EventType.REGISTERED));

            eventRepository.save(event);

            return new OrderRegisterResponse(Integer.toString(event.getOrderId()), Integer.toString(event.getEmployeeId()),
                    "ORDER REGISTERED");
        }
        return new OrderRegisterResponse(dto.getOrderId(), dto.getEmployeeId(),
                "ORDER IS ALREADY REGISTERED");
    }

    public OrderCanceledResponse cancelOrder(String orderId, OrderCancelDto dto) {
        List<EventStore> events = eventRepository.findAllByOrderId(Integer.valueOf(orderId));
        if (isRegistered(events) && !isCancelled(events) && !isIssued(events)) {
            EventStore newEvent = new EventStore();

            newEvent.setOrderId(Integer.valueOf(orderId));
            newEvent.setEmployeeId(Integer.valueOf(dto.getEmployeeId()));
            newEvent.setCreationDateTime(LocalDateTime.now());
            newEvent.setDescription(dto.getDescription());
            newEvent.setType(String.valueOf(EventType.CANCELLED));

            eventRepository.save(newEvent);

            return new OrderCanceledResponse(Integer.toString(newEvent.getOrderId()),
                    Integer.toString(newEvent.getEmployeeId()),
                    dto.getDescription(),
                    "ORDER CANCELLED");
        }
        return new OrderCanceledResponse(orderId, dto.getEmployeeId(), dto.getDescription(),
                "ORDER CAN NOT BE CANCELLED");
    }

    public OrderUpdatedResponse orderInWork(String orderId, OrderInWorkDto dto) {
        List<EventStore> events = eventRepository.findAllByOrderId(Integer.valueOf(orderId));
        if (isRegistered(events) && !isCancelled(events) && !isIssued(events) && !isInWork(events)) {
            EventStore newEvent = new EventStore();

            newEvent.setOrderId(Integer.valueOf(orderId));
            newEvent.setEmployeeId(Integer.valueOf(dto.getEmployeeId()));
            newEvent.setCreationDateTime(LocalDateTime.now());
            newEvent.setType(String.valueOf(EventType.IN_WORK));

            eventRepository.save(newEvent);

            return new OrderUpdatedResponse(Integer.toString(newEvent.getOrderId()),
                    Integer.toString(newEvent.getEmployeeId()),
                    "ORDER IN WORK");
        }
        return new OrderUpdatedResponse(orderId, dto.getEmployeeId(),
                "ORDER CAN NOT BE UPDATED");
    }

    public OrderUpdatedResponse orderReady(String orderId, OrderReadyDto dto) {
        List<EventStore> events = eventRepository.findAllByOrderId(Integer.valueOf(orderId));
        if (isRegistered(events) && !isCancelled(events) && !isIssued(events) && !isReady(events)) {
            EventStore newEvent = new EventStore();

            newEvent.setOrderId(Integer.valueOf(orderId));
            newEvent.setEmployeeId(Integer.valueOf(dto.getEmployeeId()));
            newEvent.setCreationDateTime(LocalDateTime.now());
            newEvent.setType(String.valueOf(EventType.READY));

            eventRepository.save(newEvent);

            return new OrderUpdatedResponse(Integer.toString(newEvent.getOrderId()),
                    Integer.toString(newEvent.getEmployeeId()),
                    "ORDER READY");
        }
        return new OrderUpdatedResponse(orderId, dto.getEmployeeId(),
                "ORDER CAN NOT BE READY");
    }

    public OrderUpdatedResponse issueOrder(String orderId, OrderIssueDto dto) {
        List<EventStore> events = eventRepository.findAllByOrderId(Integer.valueOf(orderId));
        if (isRegistered(events) && !isCancelled(events) && !isIssued(events) && isReady(events)) {
            EventStore newEvent = new EventStore();

            newEvent.setOrderId(Integer.valueOf(orderId));
            newEvent.setEmployeeId(Integer.valueOf(dto.getEmployeeId()));
            newEvent.setCreationDateTime(LocalDateTime.now());
            newEvent.setType(String.valueOf(EventType.ISSUED));

            eventRepository.save(newEvent);

            return new OrderUpdatedResponse(Integer.toString(newEvent.getOrderId()),
                    Integer.toString(newEvent.getEmployeeId()),
                    "ORDER ISSUED");
        }
        return new OrderUpdatedResponse(orderId, dto.getEmployeeId(),
                "ORDER CAN NOT BE ISSUED");
    }

    public GetOrderResponse getAllEventsById(String orderId) {
        List<EventStore> events = eventRepository.findAllByOrderId(Integer.valueOf(orderId));
        List<OrderEventsResponse> eventsResponse = new ArrayList<>();
        for (EventStore event : events) {
            OrderEventsResponse eventResponse = new OrderEventsResponse(
                    event.getType(),
                    event.getCreationDateTime()
            );
            eventsResponse.add(eventResponse);
        }
        EventStore event = events.get(0);
        return new GetOrderResponse(
                Integer.toString(event.getOrderId()),
                Integer.toString(event.getClientId()),
                Integer.toString(event.getEmployeeId()),
                event.getIssueDateTime(),
                Integer.toString(event.getProductId()),
                Float.toString(event.getCost()),
                event.getDescription(),
                event.getCreationDateTime(),
                events.get(events.size() - 1).getType(),
                eventsResponse
        );
    }


    private boolean isRegistered(List<EventStore> events) {
        for (EventStore event : events) {
            return Objects.equals(event.getType(), String.valueOf(EventType.REGISTERED));
        }
        return false;
    }

    private boolean isCancelled(List<EventStore> events) {
        for (EventStore event : events) {
            return Objects.equals(event.getType(), String.valueOf(EventType.CANCELLED));
        }
        return false;
    }

    private boolean isIssued(List<EventStore> events) {
        for (EventStore event : events) {
            return Objects.equals(event.getType(), String.valueOf(EventType.ISSUED));
        }
        return false;
    }

    private boolean isReady(List<EventStore> events) {
        for (EventStore event : events) {
            return Objects.equals(event.getType(), String.valueOf(EventType.READY));
        }
        return false;
    }

    private boolean isInWork(List<EventStore> events) {
        for (EventStore event : events) {
            return Objects.equals(event.getType(), String.valueOf(EventType.IN_WORK));
        }
        return false;
    }
}
