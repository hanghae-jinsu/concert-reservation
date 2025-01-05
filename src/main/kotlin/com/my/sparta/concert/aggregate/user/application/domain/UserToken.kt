package com.my.sparta.concert.aggregate.user.application.domain

import java.time.LocalDateTime

class UserToken(

    var tokenId: String,
    var userId: String,
    var isActive: Boolean,
    var createdAt: LocalDateTime,
    var expiresAt: LocalDateTime
)
