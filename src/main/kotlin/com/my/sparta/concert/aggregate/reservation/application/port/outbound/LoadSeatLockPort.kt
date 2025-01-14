package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.SeatLock

interface LoadSeatLockPort {

    fun getSeatLockByExpired(): List<SeatLock>;

}
