package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.PaymentEntity
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.UserEntity
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import org.springframework.stereotype.Component

@Component
class PaymentPersistenceMapper {

    fun mapToCreateJpaEntity(payment: Payment): PaymentEntity {
        return PaymentEntity(
            payment.paymentId,
            payment.amount,
            payment.paymentType,
            payment.transaction,
            UserEntity(payment.userId)
        )
    }

    fun mapToDomain(entity: PaymentEntity): Payment {
        return Payment(
            entity.paymentId,
            entity.userEntity.userId,
            entity.paymentType,
            entity.amount,
            entity.transaction,
        )
    }

}
