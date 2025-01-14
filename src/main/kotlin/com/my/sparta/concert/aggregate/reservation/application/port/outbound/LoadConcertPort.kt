package com.my.sparta.concert.aggregate.reservation.application.port.outbound

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert

interface LoadConcertPort {

    fun getConcertInfoById(concertId: String): Concert
    fun getConcertInfoByName(name: String): Concert
}
