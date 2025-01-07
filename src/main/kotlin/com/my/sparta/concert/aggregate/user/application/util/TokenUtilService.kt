package com.my.sparta.concert.aggregate.user.application.util

import com.my.sparta.concert.aggregate.user.application.domain.model.UserToken
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.time.LocalDateTime

@Component
class TokenUtilService {
    private val algorithm = "SHA-256"
    private val platform = "concert-reservation"

    fun generateToken(userId: String): UserToken {
        val tokenString = StringBuilder(userId + platform).toString()
        val tokenId = hashString(tokenString)
        val currentTime = LocalDateTime.now()

        return UserToken(
            tokenId = tokenId,
            userId = userId,
            isActive = false,
            createdAt = currentTime,
            expiresAt = currentTime.plusMinutes(5),
        )
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance(algorithm).digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
