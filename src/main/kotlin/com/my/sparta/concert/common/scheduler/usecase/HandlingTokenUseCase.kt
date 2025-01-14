package com.my.sparta.concert.common.scheduler.usecase

interface HandlingTokenUseCase {
    fun discardExpiredTokens()
}
