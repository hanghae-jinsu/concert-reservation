package com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.repository

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.jpa.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, String>
