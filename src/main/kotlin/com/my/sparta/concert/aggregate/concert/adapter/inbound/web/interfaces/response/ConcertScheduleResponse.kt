package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule

class ConcertScheduleResponse(

    val concertScheduleList: MutableList<ConcertSchedule>
)
