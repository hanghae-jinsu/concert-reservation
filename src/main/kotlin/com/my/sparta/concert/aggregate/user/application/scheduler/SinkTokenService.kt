package com.my.sparta.concert.aggregate.user.application.scheduler

import com.my.sparta.concert.aggregate.user.application.scheduler.usecase.TokenCacheSinkUseCase
import com.my.sparta.concert.common.util.TokenUtilService
import org.springframework.stereotype.Component

@Component
class SinkTokenService(
    private val tokenUtilService: TokenUtilService,
) : TokenCacheSinkUseCase {
    override fun deleteExpiredToken() {
        TODO("해라 좀")
    }
}
