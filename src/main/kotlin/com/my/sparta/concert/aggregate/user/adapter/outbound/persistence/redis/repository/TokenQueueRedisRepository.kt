package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.repository

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface TokenQueueRedisRepository {

    fun generateToken(userToken: UserToken): String

    fun reservationComplete(token: String): Boolean

}
