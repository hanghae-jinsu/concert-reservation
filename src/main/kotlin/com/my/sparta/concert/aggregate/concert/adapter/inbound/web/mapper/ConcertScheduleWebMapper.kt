package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.mapper

import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertInfoResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertScheduleResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertSeatResponse
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import org.springframework.stereotype.Component

@Component
class ConcertScheduleWebMapper {

    fun mapToListResponse(concertScheduleList: List<ConcertSchedule>): ConcertScheduleResponse {
        return ConcertScheduleResponse(
            concertScheduleList = mapToScheduleResponse(concertScheduleList),
        )
    }

    private fun mapToScheduleResponse(concertScheduleList: List<ConcertSchedule>): List<ConcertInfoResponse> {
        return concertScheduleList.map { concertSchedule ->
            ConcertInfoResponse(
                concertScheduleId = concertSchedule.concertScheduleId,
                concertName = concertSchedule.concertName,
                startDateTime = concertSchedule.startDateTime,
                endDateTime = concertSchedule.endDateTime,
                runningTime = concertSchedule.runningTime,
                concertSeat = mapToConcertSeat(concertSchedule)
            )
        }
    }

    private fun mapToConcertSeat(concertSchedule: ConcertSchedule): List<ConcertSeatResponse> =
        concertSchedule.concertSeat
            .filter { seat ->
                seat.seatStatus == ConcertSeat.SeatStatus.AVAILABLE
            }
            .map { seat ->
                ConcertSeatResponse(
                    seat.id
                )
            }

}
