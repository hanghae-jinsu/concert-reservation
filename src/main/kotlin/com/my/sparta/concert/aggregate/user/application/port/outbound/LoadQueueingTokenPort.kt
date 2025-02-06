package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface LoadQueueingTokenPort {

    fun loadActivatableTokens(): List<UserToken>

    fun loadExpiredTargetTokens(): List<UserToken>

}
