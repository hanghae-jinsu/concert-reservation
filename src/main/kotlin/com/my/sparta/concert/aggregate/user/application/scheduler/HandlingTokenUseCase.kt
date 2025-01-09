package com.my.sparta.concert.aggregate.user.application.scheduler

interface HandlingTokenUseCase {

    fun changeActiveTokens();

    fun discardExpiredTokens();

}
