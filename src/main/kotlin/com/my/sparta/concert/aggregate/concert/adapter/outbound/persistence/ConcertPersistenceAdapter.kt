package com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence

import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.mapper.ConcertPersistenceMapper
import com.my.sparta.concert.aggregate.concert.adapter.outbound.persistence.repository.ConcertJpaRepository
import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertPort
import jakarta.persistence.*
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ConcertPersistenceAdapter(
    private val concertRepository: ConcertJpaRepository,
    private val concertPersistenceMapper: ConcertPersistenceMapper,
) : LoadConcertPort {

    override fun getConcertInfoById(concertId: String): Concert {

        val concertEntity =
            concertRepository.findById(concertId).orElseThrow {
                EntityNotFoundException("해당 $concertId 로는 entity를 찾을 수 없습니다.")
            }

        return concertPersistenceMapper.mapToDomain(concertEntity)
    }


    override fun getConcertInfoByName(name: String): Concert {

        val concertEntity = concertRepository.findByConcertName(name).orElseThrow {
            EntityNotFoundException("해당 $name 로는 entity를 찾을 수 없습니다.");
        }

        return concertPersistenceMapper.mapToDomain(concertEntity)
    }
}
