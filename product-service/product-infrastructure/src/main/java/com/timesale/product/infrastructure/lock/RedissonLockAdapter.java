package com.timesale.product.infrastructure.lock;

import com.timesale.common.exception.BusinessException;
import com.timesale.product.application.port.DistributedLockPort;
import com.timesale.product.infrastructure.exception.LockErrorCode;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockAdapter implements DistributedLockPort {

    private final RedissonClient redissonClient;

    @Override
    public void executeWithLock(String lockKey, Runnable action) {
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean available = lock.tryLock(10, 3, TimeUnit.SECONDS);

            if (!available) {
                log.error("Redisson Lock 획득 대기 시간 초과. lockKey: {}", lockKey);
                throw new BusinessException(LockErrorCode.FAILED_ACQUIRE_LOCK);
            }

            action.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(LockErrorCode.LOCK_INTERRUPTED);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
