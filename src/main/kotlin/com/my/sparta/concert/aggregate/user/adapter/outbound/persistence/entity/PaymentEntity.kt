package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity

import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Getter
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PaymentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    val paymentId: String,

    val amount: Double,

    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,

    @Enumerated(EnumType.STRING)
    val transaction: PayingTransaction,

    @ManyToOne(fetch = FetchType.LAZY)
    val userEntity: UserEntity

)
