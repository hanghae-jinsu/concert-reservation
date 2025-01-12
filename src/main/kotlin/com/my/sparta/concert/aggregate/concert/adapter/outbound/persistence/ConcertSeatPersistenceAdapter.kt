package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertSeatPersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertSeatJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import jakarta.persistence.EntityExistsException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ConcertSeatPersistenceAdapter(
    private val concertSeatRepository: ConcertSeatJpaRepository,
    private val concertSeatPersistenceMapper: ConcertSeatPersistenceMapper,
) : LoadConcertSeatPort, SaveConcertSeatPort {
    override fun getConcertSeatDetailInfo(
        seatId: List<Int>,
        scheduleId: String,
    ) {
        concertSeatRepository.findByIdAndScheduleId(seatId = seatId, scheduleId).ifPresent {
            throw EntityExistsException("해당하는 id $seatId 는 이미 예약된 좌석 입니다.")
        }
    }

    override fun saveConcertSeat(domain: List<ConcertSeat>) {
        concertSeatRepository.saveAll(concertSeatPersistenceMapper.mapToEntities(domain))
    }
}
