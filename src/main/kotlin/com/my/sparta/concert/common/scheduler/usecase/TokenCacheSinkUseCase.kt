package com.my.sparta.concert.common.scheduler.usecase

interface TokenCacheSinkUseCase {
    fun deleteExpiredToken()
}
