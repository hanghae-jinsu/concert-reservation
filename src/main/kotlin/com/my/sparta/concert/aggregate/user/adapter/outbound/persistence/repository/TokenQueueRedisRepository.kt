package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.data.TokenAuthentication
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface TokenQueueRedisRepository {

    fun saveToken(token: UserToken): String
    fun deleteExpiredToken()
    fun findByTokenId(tokenId: String): TokenAuthentication
    fun findAllTokenId(): List<Any>
    fun findAllWaitingToken(): List<Any>
}
