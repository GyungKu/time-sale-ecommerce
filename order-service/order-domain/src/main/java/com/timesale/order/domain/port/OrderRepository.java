package com.timesale.order.domain.port;

import com.timesale.order.domain.Order;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long orderId);
}
