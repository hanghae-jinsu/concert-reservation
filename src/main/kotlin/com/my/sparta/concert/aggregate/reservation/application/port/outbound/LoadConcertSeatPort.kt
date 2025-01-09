package com.my.sparta.concert.aggregate.reservation.application.port.outbound

interface LoadConcertSeatPort {

    fun getConcertSeatDetailInfo(concertSeatId: List<Int>, concertScheduleId: String)

}
