package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.TokenPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import com.my.sparta.concert.aggregate.user.application.port.outbound.DeleteQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveUserTokenPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Slf4j
@Component
@RequiredArgsConstructor
class TokenQueuePersistenceAdapter(
    private val tokenPersistenceMapper: TokenPersistenceMapper,
    private val tokenQueueJpaRepository: TokenQueueJpaRepository,
) : SaveUserTokenPort, SaveQueueingTokenPort, LoadQueueingTokenPort, DeleteQueueingTokenPort {

    override fun saveUserToken(token: UserToken): String {
        val tokenEntity = tokenPersistenceMapper.mapToJpaEntity(token)
        val savedToken = tokenQueueJpaRepository.save(tokenEntity)

        return savedToken.tokenId
    }

    override fun loadActivatableTokens(): List<UserTokenEntity> {

        val dateTime = LocalDateTime.now();
        val pageable = PageRequest.of(0, 50)

        return tokenQueueJpaRepository.findByTokenNonExpired(dateTime, pageable);

    }

    override fun loadExpiredTargetTokens(): List<UserTokenEntity> {
        val dateTime = LocalDateTime.now();

        return tokenQueueJpaRepository.findByExpiredTargetToken(dateTime);

    }

    override fun saveTokens(tokens: List<UserTokenEntity>) {

        tokenQueueJpaRepository.saveAll(tokens);
    }

    override fun deleteTokens(tokens: List<UserTokenEntity>) {

        tokenQueueJpaRepository.deleteAll(tokens);
    }
}
