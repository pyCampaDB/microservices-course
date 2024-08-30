package com.msvc.order.order_service.repositories;

import com.msvc.order.order_service.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
