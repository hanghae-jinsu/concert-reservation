package com.my.sparta.concert.aggregate.reservation.application.port.outbound

interface LoadConcertSeatPort {

    fun getConcertSeatDetailInfo(concertSeatId: Int, concertScheduleId: String)

}
