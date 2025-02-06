package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.mapper.TokenPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.repository.TokenQueueJpaRepository
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
//@Component
@RequiredArgsConstructor
class TokenQueuePersistenceAdapter(
    private val tokenPersistenceMapper: TokenPersistenceMapper,
    private val tokenQueueJpaRepository: TokenQueueJpaRepository,
) : SaveUserTokenPort,
    SaveQueueingTokenPort,
    LoadQueueingTokenPort,
    DeleteQueueingTokenPort,
    LoadNonExpiredTokenPort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun saveUserToken(token: UserToken): String {
        val tokenEntity = tokenPersistenceMapper.mapToJpaEntity(token)
        val savedToken = tokenQueueJpaRepository.save(tokenEntity)

        return savedToken.tokenId
    }

    override fun loadActivatableTokens(): List<UserToken> {
        val dateTime = LocalDateTime.now()
        val pageable = PageRequest.of(0, 50)

        logger.info("$dateTime : loadActivatableTokens ")
        val loadUnExpiredToken = tokenQueueJpaRepository.findByTokenNonExpired(dateTime, pageable);

        return tokenPersistenceMapper.mapToDomainList(loadUnExpiredToken);
    }

    override fun loadExpiredTargetTokens(): List<UserToken> {
        val dateTime = LocalDateTime.now()

        logger.info("$dateTime : loadExpiredTargetTokens")

        val expiredTokenList = tokenQueueJpaRepository.findByExpiredTargetToken(dateTime);

        return tokenPersistenceMapper.mapToDomainList(expiredTokenList)
    }

    override fun validateActiveTokens(tokenString: Set<String>): Set<String> {
        return tokenQueueJpaRepository.findByUsedTokens(tokenString)
    }

    override fun saveTokens(tokens: List<UserToken>) {
        val tokens = tokenPersistenceMapper.mapToJpaEntities(tokens);
        tokenQueueJpaRepository.saveAll(tokens)
    }

    override fun deleteTokens(tokens: List<UserToken>) {
        val tokens = tokenPersistenceMapper.mapToJpaEntities(tokens);
        tokenQueueJpaRepository.deleteAll(tokens)
    }
}
