package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.redis

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

        tokenQueueRedisRepository.addToken(token);

        return "token successfully added"
    }

    override fun saveTokens(tokens: List<UserToken>) {
        tokens.stream().forEach { token ->
            tokenQueueRedisRepository.addToken(token)
        }
    }

    override fun loadActivatableTokens(): List<UserToken> {
      return tokenQueueRedisRepository.findActivateTokens();
    }

    override fun loadExpiredTargetTokens(): List<UserToken> {
        TODO("Not yet implemented")
    }

    override fun deleteTokens(tokens: List<UserToken>) {
        TODO("Not yet implemented")
    }

    override fun validateActiveTokens(tokenString: Set<String>): Set<String> {
        TODO("Not yet implemented")
    }
}
