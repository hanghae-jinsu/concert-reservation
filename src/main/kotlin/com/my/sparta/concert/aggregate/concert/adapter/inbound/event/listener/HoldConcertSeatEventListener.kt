package com.my.sparta.concert.aggregate.concert.adapter.inbound.event.listener

import com.my.sparta.concert.aggregate.concert.application.domain.model.event.HoldConcertSeatEvent
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class HoldConcertSeatEventListener(
    private val loadConcertSeatPort: LoadConcertSeatPort,
    private val saveConcertSeatPort: SaveConcertSeatPort
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun changeAvaliableConcertSeat(event: HoldConcertSeatEvent) {

        logger.info(" changeAvaliableConcertSeat execute ")

        val concertSeats = loadConcertSeatPort.getConcertSeatInfoList(listOf(event.seatId))

        concertSeats.forEach { concertSeat ->

            concertSeat.statusUpdate();

        }
        saveConcertSeatPort.saveAllConcertSeat(concertSeats)

    }

}
