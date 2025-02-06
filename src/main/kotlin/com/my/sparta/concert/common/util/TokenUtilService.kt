package com.my.sparta.concert.common.util

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueRedisRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class TokenUtilService(
    private val tokenQueueRedisRepository: TokenQueueRedisRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val algorithm = "SHA-256"
    private val platform = "concert-reservation"

    private val tokenCache = TokenCache<String, Boolean>(50)

    fun generateToken(userId: String): UserToken {
        val tokenString = StringBuilder(userId + platform).toString()
        val tokenId = hashString(tokenString)
        val currentTime = LocalDateTime.now()

        return UserToken(
            tokenId = tokenId,
            userId = userId,
            isActive = false,
            createdAt = currentTime,
            expiresAt = currentTime.plusMinutes(5),
        )
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance(algorithm).digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // 토큰 조회 및 검증
    fun validateToken(token: String): Boolean {
        // 캐시에서 확인
        if (tokenCache.containsKey(token)) {
            logger.info("Token found in cache: $token")
            return true
        }

        // DB 조회
        val tokenEntity = tokenQueueRedisRepository.findByTokenId(token)
        if (tokenEntity.isActive) {

            val currentTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
            // 만료 시간 확인
            if (tokenEntity.expireTime < currentTime) {
                logger.info("Token is expired: $token")
                return false
            }

            // 유효한 토큰이면 캐시에 추가
            tokenCache.put(token, true)
            logger.info("Token added to cache: $token")
            return true
        }
        return false
    }

    // 초기 캐싱
    fun loadInitialTokens() {
        logger.info("Loading tokens from database into cache...")
        val tokens = tokenQueueRedisRepository.findAllTokenId();
        tokens.forEach { token ->
            tokenCache.put(token.toString(), true)
        }
    }

    fun loadCurrentTokens(): Set<String> {
        logger.info("Loading tokens from tokenCache... $tokenCache.size()")
        return tokenCache.getAllTokens()
    }

    fun sinkCurrentTokens(tokenList: Set<String>) {
        tokenList.stream().forEach { token ->
            this.tokenCache.put(token, true)
        }
    }
}
