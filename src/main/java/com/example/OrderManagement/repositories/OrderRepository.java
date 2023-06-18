package com.example.OrderManagement.repositories;

import com.example.OrderManagement.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findOrderById(String id);
    Page<Order> findAllByStatusContainingIgnoreCase(String status, Pageable pageable);
    Page<Order> findAllByTrackingCodeContainingIgnoreCase(String trackingCode, Pageable pageable);
    Page<Order> findAll(Pageable pageable);
}
