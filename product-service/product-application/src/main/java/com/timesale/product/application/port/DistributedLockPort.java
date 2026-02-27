package com.timesale.product.application.port;

import java.util.function.Supplier;

public interface DistributedLockPort {

    <T> T executeWithLock(String lockKey, Supplier<T> action);

}
