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
        val wrapper = super.get(key) ?: return null

        val redisKey = activeUsersKey

        logger.info("redisKey: $redisKey")
        val remainingTtl = redisTemplate.connectionFactory?.connection?.pTtl(redisKey.toByteArray()) ?: -1

        if (remainingTtl > 0) {
            val totalTtl = cacheConfiguration.ttl.toMillis()
            logger.info("##############totalTtl###############")
            val ratio = remainingTtl.toDouble() / totalTtl
            if (ratio < earlyThreshold && kotlin.random.Random.nextDouble() < refreshProbability) {
                val newValue = cacheLoader(key)
                if (newValue != null) {
                    logger.info("##############cache start###############")
                    super.put(key, newValue)
                    return super.get(key)
                }
            }
        }
        return wrapper
    }
}
