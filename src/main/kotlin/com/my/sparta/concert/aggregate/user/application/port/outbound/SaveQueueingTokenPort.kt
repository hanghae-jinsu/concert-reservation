package com.my.sparta.concert.aggregate.user.application.port.outbound

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken

interface SaveQueueingTokenPort {
    fun saveTokens(tokens: List<UserToken>)
}
