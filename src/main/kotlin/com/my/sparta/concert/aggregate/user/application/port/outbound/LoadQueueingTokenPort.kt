package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity

interface LoadQueueingTokenPort {

    fun loadActivatableTokens(): List<UserTokenEntity>
    fun loadExpiredTargetTokens(): List<UserTokenEntity>
}
