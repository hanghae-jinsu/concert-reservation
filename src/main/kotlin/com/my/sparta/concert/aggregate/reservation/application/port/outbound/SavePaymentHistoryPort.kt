package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.user.application.domain.model.Payment

interface SavePaymentHistoryPort {
    fun savePaymentInfo(payment: Payment): Payment
}
