package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SavePaymentHistoryPort
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.mapper.PaymentPersistenceMapper
import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.PaymentJpaRepository
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import jakarta.persistence.*
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class PaymentPersistenceAdapter(
    private val paymentRepository: PaymentJpaRepository,
    private val paymentPersistenceMapper: PaymentPersistenceMapper,
) : SavePaymentHistoryPort {

    override fun savePaymentInfo(payment: Payment): Payment {
        val savedEntity = paymentRepository.save(paymentPersistenceMapper.mapToCreateJpaEntity(payment))
        return paymentPersistenceMapper.mapToDomain(savedEntity)
    }
}
