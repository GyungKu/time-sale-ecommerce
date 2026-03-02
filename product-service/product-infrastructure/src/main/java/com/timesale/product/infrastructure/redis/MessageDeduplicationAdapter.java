package com.timesale.product.infrastructure.redis;

import com.timesale.product.application.port.MessageDeduplicationPort;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDeduplicationAdapter implements MessageDeduplicationPort {

    private final RedissonClient redissonClient;
    private static final Long TTL_DAYS = 3L;

    @Override
    public Boolean isAlreadyProcessed(Long orderItemId) {
        RBucket<Object> bucket = redissonClient.getBucket(orderItemId.toString());
        boolean isSet = bucket.setIfAbsent("PROCESSED", Duration.ofDays(TTL_DAYS));
        return !isSet;
    }

    @Override
    public void clearProcessMark(Long orderItemId) {
        redissonClient.getBucket(orderItemId.toString()).delete();
    }
}
