package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface TokenQueueRedisRepository {

    fun saveToken(token: UserToken): String;
}
