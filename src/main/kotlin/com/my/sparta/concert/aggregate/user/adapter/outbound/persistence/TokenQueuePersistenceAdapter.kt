package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.TokenPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveUserTokenPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class TokenQueuePersistenceAdapter(
    private val tokenPersistenceMapper: TokenPersistenceMapper,
    private val tokenQueueJpaRepository: TokenQueueJpaRepository,
) : SaveUserTokenPort {
    override fun saveUserToken(token: UserToken): String {
        val tokenEntity = tokenPersistenceMapper.mapToJpaEntity(token)
        val savedToken = tokenQueueJpaRepository.save(tokenEntity)

        return savedToken.tokenId
    }
}
