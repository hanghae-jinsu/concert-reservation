package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.data

data class TokenAuthentication(
    val token: String,
    val isActive: Boolean,
    val expireTime: Long,
)
