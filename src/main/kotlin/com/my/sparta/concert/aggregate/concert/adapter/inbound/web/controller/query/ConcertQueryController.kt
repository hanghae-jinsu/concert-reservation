package com.my.sparta.concert.aggregate.concert.adapter.inbound.web.controller.query

import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.interfaces.response.ConcertScheduleResponse
import com.my.sparta.concert.aggregate.concert.adapter.inbound.web.mapper.ConcertScheduleWebMapper
import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
class ConcertQueryController(
    private val getConcertScheduleInfoPort: GetConcertScheduleInfoPort,
    private val concertScheduleWebMapper: ConcertScheduleWebMapper
) {
// concertSchedule 50명 차면 인원 마감. case 생각
    @GetMapping("{concertId}/reserve")
    fun getReservableSeats(
        @PathVariable("concertId") concertId: String,
    ): ResponseEntity<ConcertScheduleResponse> {

        val concertScheduleList = getConcertScheduleInfoPort.getConcertScheduleById(concertId);
        return ResponseEntity.ok(concertScheduleWebMapper.mapToListResponse(concertScheduleList))

    }

}
