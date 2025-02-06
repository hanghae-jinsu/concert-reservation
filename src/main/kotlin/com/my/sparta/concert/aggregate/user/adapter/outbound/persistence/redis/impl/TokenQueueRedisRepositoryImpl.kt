package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.impl

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueRedisRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TokenQueueRedisRepositoryImpl(

    private val redisTemplate: RedisTemplate<String, Any>

) : TokenQueueRedisRepository {

    private val ACTIVE_USERS_KEY = "active_users"
    private val WAITING_USERS_KEY = "waiting_users"
    private val ACTIVE_LIMIT = 50L
    private val TTL_MILLIS = 3 * 60 * 1000L // 3분

    override fun saveToken(token: UserToken): String {
        val now = System.currentTimeMillis()

        // 만료된 토큰 정리 후 추가
        cleanupExpiredTokens(now)

        val memberValue = "${token.userId}:${token.tokenId}"

        // 이미 ACTIVE 상태라면 TTL만 갱신
        val activeScore = redisTemplate.opsForZSet().score(ACTIVE_USERS_KEY, memberValue)
        if (activeScore != null) {
            redisTemplate.opsForZSet().add(ACTIVE_USERS_KEY, memberValue, (now + TTL_MILLIS).toDouble())
            return token.tokenId
        }

        // 이미 WAITING 상태라면 TTL만 갱신
        val waitingScore = redisTemplate.opsForZSet().score(WAITING_USERS_KEY, memberValue)
        if (waitingScore != null) {
            redisTemplate.opsForZSet().add(WAITING_USERS_KEY, memberValue, (now + TTL_MILLIS).toDouble())
            return token.tokenId
        }

        // 현재 ACTIVE에 몇 개 있는지 조회
        val activeCount = redisTemplate.opsForZSet().size(ACTIVE_USERS_KEY) ?: 0L
        if (activeCount < ACTIVE_LIMIT) {
            // 활성 슬롯이 남았다면 ACTIVE에 추가
            redisTemplate.opsForZSet().add(ACTIVE_USERS_KEY, memberValue, (now + TTL_MILLIS).toDouble())
        } else {
            // 아니면 WAITING으로
            redisTemplate.opsForZSet().add(WAITING_USERS_KEY, memberValue, (now + TTL_MILLIS).toDouble())
        }
        return token.tokenId
    }

    /**
     * 만료된 토큰 정리 후, 활성 슬롯이 비면 WAITING 대기열에서 채워 넣는다.
     */
    fun cleanupExpiredTokens(now: Long) {
        // score(=TTL 만료 시점)가 현재 시간보다 작거나 같으면 제거
        redisTemplate.opsForZSet().removeRangeByScore(ACTIVE_USERS_KEY, 0.0, now.toDouble())
        redisTemplate.opsForZSet().removeRangeByScore(WAITING_USERS_KEY, 0.0, now.toDouble())
        // 활성 슬롯 보충
        fillActiveSlots(now)
    }

    /**
     * ACTIVE 슬롯 남으면 WAITING 대기열에서 순서대로 꺼내서 활성화
     */
    fun fillActiveSlots(now: Long) {
        val activeCount = redisTemplate.opsForZSet().size(ACTIVE_USERS_KEY) ?: 0L
        val availableSlots = ACTIVE_LIMIT - activeCount
        if (availableSlots <= 0) {
            return
        }

        // 대기열에서 availableSlots 개수만큼 가져온 후 활성화
        val waitingTokens = redisTemplate.opsForZSet()
            .range(WAITING_USERS_KEY, 0, availableSlots - 1)

        waitingTokens?.forEach { member ->
            // WAITING에서 제거
            redisTemplate.opsForZSet().remove(WAITING_USERS_KEY, member)
            // ACTIVE로 이동 (TTL 새로 갱신)
            redisTemplate.opsForZSet().add(
                ACTIVE_USERS_KEY,
                member,
                (now + TTL_MILLIS).toDouble()
            )
        }
    }
}
