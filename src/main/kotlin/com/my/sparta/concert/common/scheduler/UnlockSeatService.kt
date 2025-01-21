package com.my.sparta.concert.common.scheduler

import com.my.sparta.concert.aggregate.concert.application.port.outbound.SaveSeatLockPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadConcertSeatPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.LoadSeatLockPort
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SaveConcertSeatPort
import com.my.sparta.concert.common.scheduler.usecase.UnlockConcertSeatUseCase
import lombok.RequiredArgsConstructor
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UnlockSeatService(
    private val loadSeatLockPort: LoadSeatLockPort,
    private val loadConcertSeatPort: LoadConcertSeatPort,
    private val saveConcertSeatPort: SaveConcertSeatPort
) : UnlockConcertSeatUseCase {

    @Scheduled(cron = " * 0/10 * * * ?")
    @Transactional
    override fun deleteHoldSeatLock() {

        val seatList = loadSeatLockPort.getSeatLockByExpired();

        val seatIdList = seatList.map { it.seatId }

        val seatInfoList = loadConcertSeatPort.getConcertSeatInfoList(seatIdList);

        seatInfoList.forEach { seat ->
            seat.statusUpdate();
        }

        saveConcertSeatPort.saveAllConcertSeat(seatInfoList)

    }
}
