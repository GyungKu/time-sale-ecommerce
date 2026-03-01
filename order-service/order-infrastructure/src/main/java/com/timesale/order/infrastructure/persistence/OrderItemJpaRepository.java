package com.timesale.order.infrastructure.persistence;

import com.timesale.order.domain.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByOrderId(Long orderId);
}
