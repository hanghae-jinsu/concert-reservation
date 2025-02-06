package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.repository

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RedisQueueRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : TokenQueueRedisRepository {

    private val ACTIVE_USERS_KEY = "active_users"
    private val WAITING_USERS_KEY = "waiting_users"
    private val ACTIVE_LIMIT = 50L
    private val TTL_MILLIS = 3 * 60 * 1000L // 3분

    @Scheduled(fixedRate = 10000)
    fun scheduledCleanup() {
        val now = System.currentTimeMillis()
        cleanupExpiredTokens(now)
    }

    // 토큰 추가: 활성 슬롯이 여유 있으면 활성화, 아니면 대기열에 추가
    override fun addToken(userToken: UserToken) {
        val now = System.currentTimeMillis()
        // 만료된 토큰 정리 후 추가
        cleanupExpiredTokens(now)

        // 이미 존재하는 토큰인 경우 TTL 갱신 후 종료
        if (redisTemplate.opsForZSet().score(ACTIVE_USERS_KEY, userToken.tokenId) != null) {
            redisTemplate.opsForZSet().add(userToken.tokenId, ACTIVE_USERS_KEY, (now +TTL_MILLIS).toDouble())
            return
        }
        if (redisTemplate.opsForZSet().score(WAITING_USERS_KEY, userToken.tokenId) != null) {
            redisTemplate.opsForZSet().add(WAITING_USERS_KEY, userToken.tokenId, (now + TTL_MILLIS).toDouble())
            return
        }

        val activeCount = redisTemplate.opsForZSet().size(ACTIVE_USERS_KEY) ?: 0L
        if (activeCount < ACTIVE_LIMIT) {
            redisTemplate.opsForZSet().add(ACTIVE_USERS_KEY, userToken.tokenId, (now + TTL_MILLIS).toDouble())
        } else {
            redisTemplate.opsForZSet().add(WAITING_USERS_KEY, userToken.tokenId, (now + TTL_MILLIS).toDouble())
        }
    }

    override fun findActivateTokens(): List<UserToken> {
        return redisTemplate.opsForZSet().range(ACTIVE_USERS_KEY, 0, -1) ?: emptySet()
    }

    // 토큰 TTL 갱신 (활성 또는 대기 상태 상관없이 갱신)
    fun renewToken(token: String) {
        val now = System.currentTimeMillis()
        if (redisTemplate.opsForZSet().score(ACTIVE_USERS_KEY, token) != null) {
            redisTemplate.opsForZSet().add(ACTIVE_USERS_KEY, token, (now + TTL_MILLIS).toDouble())
        } else if (redisTemplate.opsForZSet().score(WAITING_USERS_KEY, token) != null) {
            redisTemplate.opsForZSet().add(WAITING_USERS_KEY, token, (now + TTL_MILLIS).toDouble())
        }
    }

    // 만료된 토큰 제거 후 활성 슬롯을 보충
    fun cleanupExpiredTokens(now: Long) {
        // score가 현재 시간보다 작은 토큰 제거 (만료됨)
        redisTemplate.opsForZSet().removeRangeByScore(ACTIVE_USERS_KEY, 0.0, now.toDouble())
        redisTemplate.opsForZSet().removeRangeByScore(WAITING_USERS_KEY, 0.0, now.toDouble())
        // 활성 슬롯이 생기면 대기열 토큰을 활성화 처리
        fillActiveSlots(now)
    }

    // 활성 슬롯 보충: 활성 토큰 수가 50명 미만이면 대기열에서 토큰을 가져와 활성 상태로 전환
    fun fillActiveSlots(now: Long) {
        val activeCount = redisTemplate.opsForZSet().size(ACTIVE_USERS_KEY) ?: 0L
        val availableSlots = ACTIVE_LIMIT - activeCount
        if (availableSlots <= 0) return

        val waitingTokens = redisTemplate.opsForZSet().range(WAITING_USERS_KEY, 0, availableSlots - 1)
        waitingTokens?.forEach { token ->
            redisTemplate.opsForZSet().remove(WAITING_USERS_KEY, token)
            redisTemplate.opsForZSet().add(ACTIVE_USERS_KEY, token, (now + TTL_MILLIS).toDouble())
        }
    }

}
