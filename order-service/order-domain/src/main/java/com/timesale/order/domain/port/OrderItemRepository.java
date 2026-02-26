package com.timesale.order.domain.port;

import com.timesale.order.domain.OrderItem;

public interface OrderItemRepository {

    OrderItem save(OrderItem orderItem);
}
