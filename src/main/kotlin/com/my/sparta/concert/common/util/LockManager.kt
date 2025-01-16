package com.my.sparta.concert.common.util

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

@Component
class LockManager {

    private val locks = ConcurrentHashMap<String, ReentrantLock>()

    fun tryLock(lockKey: String, timeout: Long): ReentrantLock? {
        val lock = locks.computeIfAbsent(lockKey) { ReentrantLock() }
        return if (lock.tryLock(timeout, java.util.concurrent.TimeUnit.SECONDS)) {
            lock
        } else {

            null
        }
    }

    fun releaseLock(lockKey: String) {
        val lock = locks[lockKey]
        if (lock?.isHeldByCurrentThread == true) {
            lock.unlock()
            // 락이 더 이상 사용되지 않으면 제거
            if (!lock.isLocked) {
                locks.remove(lockKey)
            }
        }
    }


    fun <T> withLock(
        lockKey: String,
        timeout: Long = 1,
        unit: TimeUnit = TimeUnit.SECONDS,
        lockManager: LockManager,
        block: () -> T
    ): T {
        if (lockManager.tryLock(lockKey, timeout) == null) {
            throw LockAcquisitionException("Duplicate request for key: $lockKey")
        }

        return try {
            block()
        } finally {
            lockManager.releaseLock(lockKey)
        }
    }

    class LockAcquisitionException(message: String) : RuntimeException(message)
}
