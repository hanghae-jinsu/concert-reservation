package com.my.sparta.concert.aggregate.reservation.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.*
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConcertReservationFacadeTest {

    private val loadConcertPort: LoadConcertPort = mockk()
    private val loadConcertSeatPort: LoadConcertSeatPort = mockk()
    private val saveConcertSeatPort: SaveConcertSeatPort = mockk()
    private val loadUserInfoPort: LoadUserInfoPort = mockk()
    private val savePaymentHistoryPort: SavePaymentHistoryPort = mockk()
    private val saveReservationPort: SaveReservationPort = mockk()
    private val facade = ConcertReservationFacade(
        loadConcertPort,
        loadConcertSeatPort,
        saveConcertSeatPort,
        loadUserInfoPort,
        savePaymentHistoryPort,
        saveReservationPort
    )

    @Test
    fun `reserve should save reservation successfully`() {
        val userId = "user123"
        val concertId = "concert123"
        val concertScheduleId = "schedule123"
        val concertSeatNumbers = listOf(1, 2)
        val command = ConcertReservationCommand(
            userId = userId,
            concertId = concertId,
            concertScheduleId = concertScheduleId,
            concertSeatNumber = concertSeatNumbers,
            count = 2,
            paymentType = PaymentType.CARD
        )

        val concertSeats = concertSeatNumbers.map { seatNumber ->
            ConcertSeat(userId, concertScheduleId, seatNumber)
        }

        every { loadConcertSeatPort.getConcertSeatDetailInfo(concertSeatNumbers, concertScheduleId) } just Runs
        every { saveConcertSeatPort.saveConcertSeat(concertSeats) } just Runs
        every { loadConcertPort.getConcertInfoById(concertId) } returns mockk()
        every { loadUserInfoPort.getUserInfoById(userId) } returns mockk()
        every { savePaymentHistoryPort.savePaymentInfo(any()) } returns mockk()
        every { saveReservationPort.SaveReservationHistory(any()) } returns mockk()

        // Act
        val result = facade.reserve(command)

        // Assert
        verify { saveConcertSeatPort.saveConcertSeat(concertSeats) }

    }
}



