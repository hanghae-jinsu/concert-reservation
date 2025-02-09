package com.my.sparta.concert.common.config

import org.springframework.data.redis.cache.RedisCache
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.lang.Nullable

class PerRedisCacheManager(
    private val connectionFactory: RedisConnectionFactory,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val earlyThreshold: Double,
    private val refreshProbability: Double,
    private val loaderFunction: (cacheName: String, key: Any) -> Any?,
    defaultConfiguration: RedisCacheConfiguration,
) : RedisCacheManager(
        RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
        defaultConfiguration,
    ) {
    override fun createRedisCache(
        name: String,
        @Nullable cacheConfiguration: RedisCacheConfiguration?,
    ): RedisCache {
        val cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory)
        return PerRedisCache(
            name = name,
            cacheWriter = cacheWriter,
            cacheConfiguration = cacheConfiguration,
            redisTemplate = redisTemplate,
            earlyThreshold = earlyThreshold,
            refreshProbability = refreshProbability,
        ) { key ->
            loaderFunction(name, key)
        }
    }
}
