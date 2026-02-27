package com.timesale.product.application.port;

public interface DistributedLockPort {

    void executeWithLock(String lockKey, Runnable action);

}
