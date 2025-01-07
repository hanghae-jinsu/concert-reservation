package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.valueobject.WalletValueObject
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

)
