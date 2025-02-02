package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.valueobject.WalletValueObject
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import java.math.BigDecimal

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
                BigDecimal(0),
            ),
    )
}
