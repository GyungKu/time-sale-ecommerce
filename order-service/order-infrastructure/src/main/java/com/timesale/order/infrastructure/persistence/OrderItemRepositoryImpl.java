package com.timesale.order.infrastructure.persistence;

import com.timesale.order.domain.OrderItem;
import com.timesale.order.domain.port.OrderItemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaRepository jpaRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return jpaRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> findByOrderId(Long orderId) {
        return jpaRepository.findByOrderId(orderId);
    }
}
