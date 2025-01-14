package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.mapper

import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertScheduleResponse
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import org.springframework.stereotype.Component

@Component
class ConcertScheduleWebMapper {
    fun mapToListResponse(concertScheduleList: List<ConcertSchedule>): ConcertScheduleResponse {
        return ConcertScheduleResponse(
            concertScheduleList = concertScheduleList.toMutableList(),
        )
    }
}
