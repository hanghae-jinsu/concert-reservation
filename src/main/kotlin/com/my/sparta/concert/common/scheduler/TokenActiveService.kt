package com.my.sparta.concert.common.scheduler

import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.SaveQueueingTokenPort
import com.my.sparta.concert.common.scheduler.usecase.ChangeActiveTokenUseCase
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
class TokenActiveService(
    private val loadQueueingTokenPort: LoadQueueingTokenPort,
    private val saveQueueingTokenPort: SaveQueueingTokenPort,
) : ChangeActiveTokenUseCase {
    @Scheduled(cron = "0 */1 * * * ?")
    @Transactional
    override fun changeActiveTokens() {
        val tokens = loadQueueingTokenPort.loadActivatableTokens()
        for (token in tokens) {
            token.isActive = true
        }
        saveQueueingTokenPort.saveTokens(tokens)
    }
}
