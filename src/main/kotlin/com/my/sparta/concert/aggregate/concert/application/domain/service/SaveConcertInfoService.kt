package com.my.sparta.concert.aggregate.concert.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.concert.application.port.inbound.SaveConcertInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@RequiredArgsConstructor
class SaveConcertInfoService(
    private val loadConcertSeatPort: LoadConcertSeatPort,
    private val saveConcertSeatPort: SaveConcertSeatPort,
) : SaveConcertInfoUseCase {

    @Transactional
    override fun saveConcertSeat(command: ConcertReservationCommand): List<ConcertSeat> {
        // 콘서트 자리 있는지 확인
        loadConcertSeatPort.getConcertSeatDetailInfo(command.concertSeatNumber, command.concertScheduleId);

        val concertSeat =
            command.concertSeatNumber.map { value ->
                ConcertSeat(command.userId, command.concertScheduleId, value, false)
            }

        return saveConcertSeatPort.saveConcertSeat(concertSeat)

    }
}
