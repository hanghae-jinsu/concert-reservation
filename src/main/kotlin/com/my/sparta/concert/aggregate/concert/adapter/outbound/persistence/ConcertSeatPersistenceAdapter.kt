package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertSeatPersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertSeatJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import jakarta.persistence.*
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ConcertSeatPersistenceAdapter(
    private val concertSeatRepository: ConcertSeatJpaRepository,
    private val concertSeatPersistenceMapper: ConcertSeatPersistenceMapper,
) : LoadConcertSeatPort, SaveConcertSeatPort {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun getConcertSeatDetailInfo(
        seatId: Int,
        scheduleId: String,
    ) {
        concertSeatRepository.findByIdAndScheduleId(seatId = seatId, scheduleId).ifPresent {
            throw EntityExistsException("해당하는 id $seatId 는 이미 예약된 좌석 입니다.")
        }
    }

    override fun getConcertSeatInfoList(seatIdList: List<Int>): List<ConcertSeat> {

        val seats = seatIdList.mapNotNull { id ->
            concertSeatRepository.findById(id.toLong()).orElse(null)
        }

        return concertSeatPersistenceMapper.mapToDomainList(seats);

    }

    override fun saveConcertSeat(domain: ConcertSeat): ConcertSeat {
        val savedAllEntity = concertSeatRepository.save(concertSeatPersistenceMapper.mapToEntity(domain))
        return concertSeatPersistenceMapper.mapToDomain(savedAllEntity);
    }

    override fun saveAllConcertSeat(seatInfoList: List<ConcertSeat>) {

        concertSeatRepository.saveAll(concertSeatPersistenceMapper.mapToEntities(seatInfoList))

    }
}
