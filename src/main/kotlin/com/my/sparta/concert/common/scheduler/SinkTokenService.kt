package com.my.sparta.concert.common.scheduler

import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadNonExpiredTokenPort
import com.my.sparta.concert.common.scheduler.usecase.TokenCacheSinkUseCase
import com.my.sparta.concert.common.util.TokenUtilService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SinkTokenService(
    private val tokenUtilService: TokenUtilService,
    private val loadNonExpiredTokenPort: LoadNonExpiredTokenPort
) : TokenCacheSinkUseCase {

    @Scheduled(cron = "0 */3 * * * ?")
    override fun deleteExpiredToken() {

        val tokens = tokenUtilService.loadCurrentTokens();

        val useTokens = loadNonExpiredTokenPort.validateActiveTokens(tokens);

        tokenUtilService.sinkCurrentTokens(useTokens);

    }
}
