package com.timesale.order.domain.port;

import com.timesale.order.domain.OrderItem;
import java.util.Optional;

public interface OrderItemRepository {

    OrderItem save(OrderItem orderItem);

    Optional<OrderItem> findByOrderId(Long orderId);
}
