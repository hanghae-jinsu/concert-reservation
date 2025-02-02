package com.my.sparta.concert.aggregate.integration

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedissonTestContainerIntegerationTest() {
    @Autowired private lateinit var redissonClient: RedissonClient

    @Test
    fun `should set and get value in Redis using Redisson`() {
        // Redisson 클라이언트를 사용하여 데이터 저장
        val bucket: RBucket<String> = redissonClient.getBucket("testKey")
        bucket.set("testValue")

        // 저장된 데이터 확인
        val retrievedValue = bucket.get()
        assertEquals("testValue", retrievedValue)
    }
}
