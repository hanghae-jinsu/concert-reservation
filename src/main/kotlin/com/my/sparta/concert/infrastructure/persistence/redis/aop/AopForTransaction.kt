package com.my.sparta.concert.infrastructure.persistence.redis.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class AopForTransaction {

    private val log = LoggerFactory.getLogger(AopForTransaction::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Throws(Throwable::class)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {

        val methodName = joinPoint.signature.toShortString()

        log.info("Transaction started: {}", methodName)

        return try {
            val result = joinPoint.proceed()
            log.info("Transaction committed successfully: {}", methodName)
            result
        } catch (ex: Exception) {
            log.error("Transaction rolled back due to exception: {}, Exception: {}", methodName, ex.message, ex)
            throw ex
        } finally {
            log.info("Transaction finished: {}", methodName)
        }
    }

}
