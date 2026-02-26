package com.timesale.order.domain.port;

import com.timesale.order.domain.Order;

public interface OrderRepository {

    Order save(Order order);
}
