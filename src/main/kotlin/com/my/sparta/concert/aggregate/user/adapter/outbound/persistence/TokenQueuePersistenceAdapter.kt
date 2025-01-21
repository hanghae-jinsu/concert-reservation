package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserTokenEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.TokenPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import com.my.sparta.concert.aggregate.user.application.port.outbound.*
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Slf4j
@Component
@RequiredArgsConstructor
class TokenQueuePersistenceAdapter(
    private val tokenPersistenceMapper: TokenPersistenceMapper,
    private val tokenQueueJpaRepository: TokenQueueJpaRepository,
) : SaveUserTokenPort, SaveQueueingTokenPort, LoadQueueingTokenPort, DeleteQueueingTokenPort,
    LoadNonExpiredTokenPort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun saveUserToken(token: UserToken): String {
        val tokenEntity = tokenPersistenceMapper.mapToJpaEntity(token)
        val savedToken = tokenQueueJpaRepository.save(tokenEntity)

        return savedToken.tokenId
    }

    override fun loadActivatableTokens(): List<UserTokenEntity> {
        val dateTime = LocalDateTime.now()
        val pageable = PageRequest.of(0, 50)

        logger.info("$dateTime : loadActivatableTokens ")

        return tokenQueueJpaRepository.findByTokenNonExpired(dateTime, pageable)
    }

    override fun loadExpiredTargetTokens(): List<UserTokenEntity> {
        val dateTime = LocalDateTime.now()

        logger.info("$dateTime : loadExpiredTargetTokens")
        return tokenQueueJpaRepository.findByExpiredTargetToken(dateTime)
    }

    override fun validateActiveTokens(tokenString: Set<String>): Set<String> {

        return tokenQueueJpaRepository.findByUsedTokens(tokenString);

    }

    override fun saveTokens(tokens: List<UserTokenEntity>) {
        tokenQueueJpaRepository.saveAll(tokens)
    }

    override fun deleteTokens(tokens: List<UserTokenEntity>) {
        tokenQueueJpaRepository.deleteAll(tokens)
    }
}
