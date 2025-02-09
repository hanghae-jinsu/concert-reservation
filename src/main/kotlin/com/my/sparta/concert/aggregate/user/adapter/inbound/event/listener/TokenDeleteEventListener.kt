package com.my.sparta.concert.aggregate.user.adapter.inbound.event.listener

import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.ExecuteDeleteTokenEvent
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueRedisRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class TokenDeleteEventListener(
    private val redisRepository: TokenQueueRedisRepository,
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun justTokenDeleted(token: ExecuteDeleteTokenEvent) {
        val tokenValue = redisRepository.findByTokenId(token.tokenValue)
        redisRepository.deleteTokenByTokenId(tokenValue)
    }
}
