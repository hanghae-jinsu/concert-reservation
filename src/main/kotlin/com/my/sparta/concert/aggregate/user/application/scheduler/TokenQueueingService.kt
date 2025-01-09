package com.my.sparta.concert.aggregate.user.application.scheduler

import com.my.sparta.concert.aggregate.user.application.port.outbound.DeleteQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveQueueingTokenPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@RequiredArgsConstructor
class TokenQueueingService(
    private val loadQueueingTokenPort: LoadQueueingTokenPort,
    private val saveQueueingTokenPort: SaveQueueingTokenPort,
    private val deleteQueueingTokenPort: DeleteQueueingTokenPort
) : HandlingTokenUseCase {

    @Scheduled(cron = "0 */1 * * * ?")
    @Transactional
    override fun changeActiveTokens() {

        val tokens = loadQueueingTokenPort.loadActivatableTokens();
        for (token in tokens) {
            token.isActive = true;
        }
        saveQueueingTokenPort.saveTokens(tokens);

    }

    @Scheduled(cron = "30 */1 * * * ?")
    @Transactional
    override fun discardExpiredTokens() {

        val tokens = loadQueueingTokenPort.loadExpiredTargetTokens();

        deleteQueueingTokenPort.deleteTokens(tokens);

    }
}