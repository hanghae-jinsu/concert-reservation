package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.valueobject.WalletValueObject
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val userId: String,
    val userName: String,
    val age: Int,
    @Embedded
    val walletValueObject: WalletValueObject,
) {
    constructor(userId: String) : this(
        userId = userId,
        userName = "",
        age = 0,
        walletValueObject =
            WalletValueObject(
                PaymentType.CARD,
                0.0,
            ),
    )
}
