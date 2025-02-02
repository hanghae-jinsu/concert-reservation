package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.repository

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.ZoneOffset

@Component
class RedisQueueRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : TokenQueueRedisRepository {


    override fun generateToken(userToken: UserToken): String {

        val tokenKey = userToken.tokenId
        val tokenValue = userToken.userId
        val expirationScore = userToken.createdAt.toEpochSecond(ZoneOffset.UTC)
        redisTemplate.opsForZSet().add(tokenKey, tokenValue, expirationScore.toDouble())
        redisTemplate.expire(tokenKey, Duration.ofMinutes(3))

        return tokenValue;
    }

    override fun reservationComplete(userId: String): Boolean {
        val removedCount = redisTemplate.opsForZSet().remove(userId)

        if (removedCount != null) {
            return removedCount > 0
        }
        return false
    }


}
