package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, String> {
}
