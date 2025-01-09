package com.my.sparta.concert.aggregate.user.application.scheduler.usecase

interface HandlingTokenUseCase {


    fun discardExpiredTokens();

}
