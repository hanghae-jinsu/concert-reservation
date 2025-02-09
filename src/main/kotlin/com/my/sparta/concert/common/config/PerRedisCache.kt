package com.my.sparta.concert.common.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.data.redis.cache.RedisCache
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.core.RedisTemplate

class PerRedisCache(
    name: String,
    cacheWriter: RedisCacheWriter,
    cacheConfiguration: RedisCacheConfiguration?,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val earlyThreshold: Double,
    private val refreshProbability: Double,
    private val cacheLoader: (Any) -> Any?,
) : RedisCache(name, cacheWriter, cacheConfiguration!!) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val activeUsersKey = "active_users"

    override fun get(key: Any): Cache.ValueWrapper? {
        // 1. 기존 캐시 값 조회 (초기 load의 경우 캐시 미스일 수 있음)
        var wrapper = super.get(key)
        if (wrapper == null) {
            logger.info("캐시 미스 - 초기 로드 진행, key: $key")
            val newValue = cacheLoader(key)
            if (newValue != null) {
                super.put(key, newValue)
                wrapper = super.get(key)
            }
            return wrapper
        }

        // 2. 캐시 히트인 경우, TTL 기반으로 갱신 여부 결정
        // (여기서는 activeUsersKey를 사용하고 있으므로 캐시의 TTL이 이 키에 의존한다고 가정)
        val redisKeyBytes = activeUsersKey.toByteArray()
        val remainingTtl = redisTemplate.connectionFactory
            ?.connection
            ?.pTtl(redisKeyBytes) ?: -1L
        logger.info("Redis 키: $activeUsersKey, 남은 TTL: $remainingTtl ms")

        // 3. 만료 기간이 유효하면 전체 TTL 대비 경과 비율을 계산 (경과비율 = 1 - remainingTTL / totalTTL)
        if (remainingTtl > 0) {
            val totalTtlMillis = cacheConfiguration.ttl.toMillis()
            val elapsedRatio = 1.0 - (remainingTtl.toDouble() / totalTtlMillis)
            logger.info("경과 비율: $elapsedRatio (갱신 임계치: $earlyThreshold)")

            // 4. 만약 경과 비율이 설정 임계치 이상이면 캐시를 갱신 (즉, DB에서 새로 값을 가져옴)
            if (elapsedRatio >= earlyThreshold) {
                logger.info("캐시 갱신 조건 충족 - key: $key, elapsedRatio: $elapsedRatio")
                val newValue = cacheLoader(key)
                if (newValue != null) {
                    super.put(key, newValue)
                    wrapper = super.get(key)
                } else {
                    logger.warn("갱신 시도했으나 cacheLoader가 null 반환 - key: $key")
                }
            }
        } else {
            logger.warn("유효하지 않은 TTL 값 - remainingTtl: $remainingTtl, 캐시 갱신 생략")
        }

        // 5. 최종 캐시 값을 반환
        return wrapper
    }
}
