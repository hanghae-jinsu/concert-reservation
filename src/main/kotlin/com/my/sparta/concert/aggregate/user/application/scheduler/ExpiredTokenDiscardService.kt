package com.my.sparta.concert.aggregate.user.application.scheduler

import com.my.sparta.concert.aggregate.user.application.port.outbound.DeleteQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadQueueingTokenPort
import com.my.sparta.concert.aggregate.user.application.scheduler.usecase.HandlingTokenUseCase
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
class ExpiredTokenDiscardService(
    private val loadQueueingTokenPort: LoadQueueingTokenPort,
    private val deleteQueueingTokenPort: DeleteQueueingTokenPort,
) : HandlingTokenUseCase {
    @Scheduled(cron = "0/30 * * * * ?")
    @Transactional
    override fun discardExpiredTokens() {
        val tokens = loadQueueingTokenPort.loadExpiredTargetTokens()

        deleteQueueingTokenPort.deleteTokens(tokens)
    }
}
