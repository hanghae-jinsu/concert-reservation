package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertSeatPersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertSeatJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import jakarta.persistence.*
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ConcertSeatPersistenceAdapter(
    private val concertSeatRepository: ConcertSeatJpaRepository,
    private val concertSeatPersistenceMapper: ConcertSeatPersistenceMapper,
) : LoadConcertSeatPort, SaveConcertSeatPort {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun getConcertSeatDetailInfo(
        seatId: Int,
        scheduleId: String,
    ) {
        val status = listOf(ConcertSeat.SeatStatus.RESERVED, ConcertSeat.SeatStatus.HOLD)
        logger.info("@@@ 예약된 좌석 여부 확인 @@@")
        concertSeatRepository.findByIdAndScheduleId(seatId = seatId, scheduleId, status).ifPresent {
            throw EntityExistsException("해당하는 id $seatId 는 이미 예약된 좌석 입니다.")
        }
    }

    override fun getConcertSeatInfoList(seatIdList: List<Int>): List<ConcertSeat> {
        val status = ConcertSeat.SeatStatus.HOLD
        val seats =
            seatIdList.mapNotNull { id ->
                concertSeatRepository.findByIdWithStatus(id.toLong(), status)
            }

        return concertSeatPersistenceMapper.mapToDomainList(seats)
    }

    override fun saveConcertSeat(domain: ConcertSeat): ConcertSeat {
        val savedEntity = concertSeatRepository.save(concertSeatPersistenceMapper.mapToEntity(domain))
        return concertSeatPersistenceMapper.mapToDomain(savedEntity)
    }

    override fun saveAllConcertSeat(seatInfoList: List<ConcertSeat>) {
        concertSeatRepository.saveAll(concertSeatPersistenceMapper.mapToEntities(seatInfoList))
    }
}
