package com.timesale.order.application.port;

public interface MessageQueuePort {

    <T> void publish(String topic, T message);

}
