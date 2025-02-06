package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface DeleteQueueingTokenPort {
    fun deleteTokens(tokens: List<UserToken>)
}
