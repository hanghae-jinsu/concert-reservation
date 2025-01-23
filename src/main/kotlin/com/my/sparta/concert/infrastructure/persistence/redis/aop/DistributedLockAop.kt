package com.my.sparta.concert.infrastructure.persistence.redis.aop

import lombok.RequiredArgsConstructor
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
@RequiredArgsConstructor
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction
) {

    private val redisLockPrefix = "LOCK:"
    private val log = LoggerFactory.getLogger(DistributedLockAop::class.java)

    @Around("@annotation(DistributedLock)")
    @Throws(Throwable::class)
    fun lock(joinPoint: ProceedingJoinPoint): Any? {

        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(DistributedLock::class.java)

        val key = redisLockPrefix + CustomSpringELParser.getDynamicValue(
            signature.parameterNames,
            joinPoint.args,
            distributedLock.key
        )

        val rLock: RLock = redissonClient.getLock(key)

        return try {
            val available = rLock.tryLock(
                distributedLock.waitTime,
                distributedLock.leaseTime,
                distributedLock.timeUnit
            )

            if (!available) {
                return false
            }

            aopForTransaction.proceed(joinPoint)
        } catch (e: InterruptedException) {
            throw e
        } finally {
            try {
                if (rLock.isHeldByCurrentThread) {
                    rLock.unlock()
                }
            } catch (e: IllegalMonitorStateException) {
                log.info(
                    "Redisson Lock Already Unlocked. serviceName={}, key={}",
                    method.name,
                    key
                )
            }
        }
    }

}
