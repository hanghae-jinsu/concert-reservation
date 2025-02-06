package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.mapper

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.springframework.stereotype.Component

@Component
class TokenPersistenceMapper {

    fun mapToJpaEntity(token: UserToken): UserTokenEntity {
        return UserTokenEntity(
            token.tokenId,
            token.userId,
            token.isActive,
            token.createdAt,
            token.expiresAt,
        )
    }

    fun mapToDomainList(loadUnExpiredToken: List<UserTokenEntity>): List<UserToken> {
        return loadUnExpiredToken.stream().map(this::mapToDomain).toList();
    }

    private fun mapToDomain(userTokenEntity: UserTokenEntity): UserToken {
        return UserToken(
            tokenId = userTokenEntity.tokenId,
            userId = userTokenEntity.userId,
            isActive = userTokenEntity.isActive,
            createdAt = userTokenEntity.createdAt,
            expiresAt = userTokenEntity.expiresAt
        )
    }

    fun mapToJpaEntities(tokens: List<UserToken>): List<UserTokenEntity> {
        return tokens.stream().map(this::mapToJpaEntity).toList();
    }
}
