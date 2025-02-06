package com.my.sparta.concert.common.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

@EnableCaching
@Configuration
class RedisCacheConfig(
    private val redisConnectionFactory: RedisConnectionFactory,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val ACTIVE_USERS_KEY = "active_users"
    private val logger: Logger = LoggerFactory.getLogger(javaClass);

    @Bean
    fun perRedisCacheManager(): RedisCacheManager {
        val defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
            .disableCachingNullValues()

        return PerRedisCacheManager(
            connectionFactory = redisConnectionFactory,
            redisTemplate = redisTemplate,
            earlyThreshold = 0.1,        // 남은 TTL이 10% 이하라면
            refreshProbability = 0.3,    // 30% 확률로 원본 갱신 시도
            loaderFunction = { cacheName, key ->
                fetchFromDatabase(cacheName, key)
            },
            defaultConfiguration = defaultConfig
        )

    }

    private fun fetchFromDatabase(cacheName: String, key: Any): Any? {

        val allMembers = redisTemplate.opsForZSet()
            .range(ACTIVE_USERS_KEY, 0, -1) ?: emptySet()
        logger.info("############### fetchFromDatabase  ################")
        return allMembers.toList()

    }


}
