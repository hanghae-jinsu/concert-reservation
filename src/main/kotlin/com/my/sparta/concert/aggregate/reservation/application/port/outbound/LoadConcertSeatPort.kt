package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat

interface LoadConcertSeatPort {

    fun getConcertSeatDetailInfo(
        concertSeatId: Int,
        concertScheduleId: String,
    )

    fun getConcertSeatInfoList(seatIdList: List<Int>): List<ConcertSeat>
}
