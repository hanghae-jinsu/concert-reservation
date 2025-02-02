package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis.repository.TokenQueueRedisRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import com.my.sparta.concert.aggregate.user.application.port.outbound.*
import org.springframework.stereotype.Component


@Component
class TokenQueueRedisAdapter(
    private val tokenQueueRedisRepository: TokenQueueRedisRepository
) : SaveUserTokenPort,
    SaveQueueingTokenPort,
    LoadQueueingTokenPort,
    DeleteQueueingTokenPort,
    LoadNonExpiredTokenPort {

    override fun saveUserToken(token: UserToken): String {
        TODO("Not yet implemented")
    }

    override fun saveTokens(tokens: List<UserTokenEntity>) {
        TODO("Not yet implemented")
    }

    override fun loadActivatableTokens(): List<UserTokenEntity> {
        TODO("Not yet implemented")
    }

    override fun loadExpiredTargetTokens(): List<UserTokenEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteTokens(tokens: List<UserTokenEntity>) {
        TODO("Not yet implemented")
    }

    override fun validateActiveTokens(tokenString: Set<String>): Set<String> {
        TODO("Not yet implemented")
    }
}
