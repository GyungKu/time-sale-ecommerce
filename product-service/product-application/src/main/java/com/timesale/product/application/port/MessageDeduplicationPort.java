package com.timesale.product.application.port;

public interface MessageDeduplicationPort {

    Boolean isAlreadyProcessed(Long orderItemId);

    void clearProcessMark(Long orderItemId);
}
