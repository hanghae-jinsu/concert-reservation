package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
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

}
