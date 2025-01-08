package com.my.sparta.concert.aggregate.concert.application.port.outbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule

interface GetConcertScheduleInfoPort {

    fun getConcertScheduleById(concertId: String): List<ConcertSchedule>

}
