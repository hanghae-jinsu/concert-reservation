package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat

interface SaveConcertSeatPort {

    fun saveConcertSeat(domain: ConcertSeat): ConcertSeat
    fun saveAllConcertSeat(seatInfoList: List<ConcertSeat>)
}
