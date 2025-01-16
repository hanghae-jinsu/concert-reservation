package com.my.sparta.concert.aggregate.concert.adapter.inbound.event.listener

import com.my.sparta.concert.aggregate.reservation.application.domain.model.event.ChangeStatusUseSeatEvent
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UnlockConcertSeatEventListener(
    private val loadConcertSeatPort: LoadConcertSeatPort,
    private val saveConcertSeatPort: SaveConcertSeatPort
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun changeConcertSeatStatus(event: ChangeStatusUseSeatEvent) {

        logger.info(" changeConcertSeatStatus execute ")

        val concertSeats = loadConcertSeatPort.getConcertSeatInfoList(listOf(event.seatId))

        concertSeats.forEach { concertSeat ->

            concertSeat.statusUpdate();

        }
        saveConcertSeatPort.saveAllConcertSeat(concertSeats)

    }
}
