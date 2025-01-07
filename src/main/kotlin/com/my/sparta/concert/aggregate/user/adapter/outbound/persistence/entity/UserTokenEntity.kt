package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class UserTokenEntity constructor(
    @Id
    var tokenId: String,
    var userId: String,
    var isActive: Boolean,
    var createdAt: LocalDateTime,
    var expiresAt: LocalDateTime,
)
