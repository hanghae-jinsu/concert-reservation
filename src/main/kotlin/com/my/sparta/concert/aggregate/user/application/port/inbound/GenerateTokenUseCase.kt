package com.my.sparta.concert.aggregate.user.application.port.inbound

interface GenerateTokenUseCase {
    fun generateToken(userId: String): String
}
