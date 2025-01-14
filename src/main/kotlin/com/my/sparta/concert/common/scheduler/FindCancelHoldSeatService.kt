package com.my.sparta.concert.common.scheduler

import com.my.sparta.concert.aggregate.concert.application.domain.model.event.HoldConcertSeatEvent
import com.my.sparta.concert.aggregate.concert.application.port.outbound.SaveSeatLockPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadSeatLockPort
import com.my.sparta.concert.common.scheduler.usecase.CancelHoldConcertSeatUseCase
import lombok.RequiredArgsConstructor
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class FindCancelHoldSeatService(
    private val loadSeatLockPort: LoadSeatLockPort,
    private val saveSeatLockPort: SaveSeatLockPort,
    private val eventPublisher: ApplicationEventPublisher,
) : CancelHoldConcertSeatUseCase {

    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional
    override fun changeAvailableSeat() {

        val seatList = loadSeatLockPort.getSeatLockByExpired();

        val eventList = seatList.map { seat -> HoldConcertSeatEvent(seat.seatId, seat.userId) }

        eventPublisher.publishEvent(eventList);

        saveSeatLockPort.deleteSeatLocks(seatList);

    }
}
