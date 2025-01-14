package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertSchedulePersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertScheduleJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSchedule
import com.my.sparta.concert.aggregate.concert.application.port.outbound.GetConcertScheduleInfoPort
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ConcertScheduleSchedulePersistenceAdapter(
    private val concertScheduleRepository: ConcertScheduleJpaRepository,
    private val concertSchedulePersistenceMapper: ConcertSchedulePersistenceMapper,
) : GetConcertScheduleInfoPort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun getConcertScheduleById(concertId: String): List<ConcertSchedule> {
        val concertScheduleList = concertScheduleRepository.findByConcertId(concertId)

        return concertSchedulePersistenceMapper.mapToDomainList(concertScheduleList)
    }
}
