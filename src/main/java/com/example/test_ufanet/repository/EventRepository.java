package com.example.test_ufanet.repository;

import com.example.test_ufanet.data.event.EventStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventStore, Integer> {

    List<EventStore> findAllByOrderId(Integer orderId);
    EventStore findByOrderId(Integer orderId);
}
