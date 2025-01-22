package com.my.sparta.concert.aggregate

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.testcontainers.containers.GenericContainer

@TestConfiguration
class RedisTestConfig {

    companion object {
        private val REDIS_CONTAINER = GenericContainer<Nothing>("redis:5.0.3-alpine").apply {
            withExposedPorts(6379)
            start()
        }

        val redisHost: String
            get() = REDIS_CONTAINER.host

        val redisPort: Int
            get() = REDIS_CONTAINER.getMappedPort(6379)
    }
//    @Bean
//    fun reactiveRedisTemplate(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
//        val serializer = Jackson2JsonRedisSerializer(Any::class.java)
//        val builder = RedisSerializationContext.newSerializationContext<String, Any>(StringRedisSerializer())
//        val context = builder.value(serializer).build()
//
//        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, context)
//    }
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config().apply {
            useSingleServer().apply {
                address = "redis://$redisHost:$redisPort"
            }
        }
        return Redisson.create(config)
    }
}
