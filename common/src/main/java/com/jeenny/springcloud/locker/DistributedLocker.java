package com.jeenny.springcloud.locker;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/5/5.
 */
public interface DistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}
