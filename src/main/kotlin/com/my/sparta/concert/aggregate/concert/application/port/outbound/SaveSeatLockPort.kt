package com.my.sparta.concert.aggregate.concert.application.port.outbound

import com.my.sparta.concert.aggregate.reservation.application.domain.model.SeatLock

interface SaveSeatLockPort {

    fun saveHoldSeatInfo(seatLock: SeatLock)

}
