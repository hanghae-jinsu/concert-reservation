//package com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence
//
//import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.entity.ReservationEntity
//import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.mapper.ReservationPersistenceMapper
//import com.my.sparta.concert.aggregate.reservation.adapter.outbound.persistence.repository.ReservationJpaRepository
//import com.my.sparta.concert.aggregate.reservation.application.domain.model.Reservation
//import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.BuyerInfo
//import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ChargeInfo
//import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ConcertInfo
//import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
//import io.mockk.*
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//
//class ReservationPersistenceAdapterTest {
//    private val reservationRepository: ReservationJpaRepository = mockk()
//    private val reservationPersistenceMapper: ReservationPersistenceMapper = mockk()
//    private val adapter = ReservationPersistenceAdapter(reservationRepository, reservationPersistenceMapper)
//
//    @Test
//    fun `SaveReservationHistory should map, save, and return domain reservations`() {
//        // Arrange
//        val chargeInfo = ChargeInfo(paymentId = "payment123", paymentType = PaymentType.CARD)
//        val concertInfo =
//            ConcertInfo(
//                concertId = "concert123",
//                concertName = "Amazing Concert",
//                seatInfo = mockk(),
//                runningTime = 120,
//            )
//        val buyerInfo =
//            BuyerInfo(
//                userId = "user123",
//                cost = 150.0,
//                count = 2,
//                targetAge = 18,
//            )
//        val domainReservations =
//            listOf(
//                Reservation("reservation1", chargeInfo, concertInfo, buyerInfo),
//                Reservation("reservation2", chargeInfo, concertInfo, buyerInfo),
//            )
//
//        val reservationEntities =
//            listOf(
//                ReservationEntity(
//                    reservationId = "reservation1",
//                    paymentId = "payment123",
//                    paymentType = PaymentType.CARD,
//                    concertId = "concert123",
//                    concertName = "Amazing Concert",
//                    seatId = 101,
//                    runningTime = 120,
//                    userId = "user123",
//                    cost = 150.0,
//                    count = 2,
//                    targetAge = 18,
//                ),
//                ReservationEntity(
//                    reservationId = "reservation2",
//                    paymentId = "payment123",
//                    paymentType = PaymentType.CARD,
//                    concertId = "concert123",
//                    concertName = "Amazing Concert",
//                    seatId = 102,
//                    runningTime = 120,
//                    userId = "user123",
//                    cost = 150.0,
//                    count = 2,
//                    targetAge = 18,
//                ),
//            )
//
//        val savedEntities = reservationEntities
//
//        val mappedBackDomainReservations =
//            listOf(
//                Reservation("reservation1", chargeInfo, concertInfo, buyerInfo),
//                Reservation("reservation2", chargeInfo, concertInfo, buyerInfo),
//            )
//
//        every { reservationPersistenceMapper.mapToJpaEntities(domainReservations) } returns reservationEntities
//        every { reservationRepository.saveAll(reservationEntities) } returns savedEntities
//        every { reservationPersistenceMapper.mapToDomainList(savedEntities) } returns mappedBackDomainReservations
//
//        // Act
//        val result = adapter.saveReservationHistory(domainReservations)
//
//        // Assert
//        assertEquals(2, result.size)
//        assertEquals("reservation1", result[0].reservationId)
//        assertEquals("reservation2", result[1].reservationId)
//        assertEquals("payment123", result[0].chargeInfo.paymentId)
//        assertEquals(PaymentType.CARD, result[0].chargeInfo.paymentType)
//        assertEquals("concert123", result[0].concertInfo.concertId)
//        assertEquals("Amazing Concert", result[0].concertInfo.concertName)
//        assertEquals("user123", result[0].buyerInfo.userId)
//        assertEquals(150.0, result[0].buyerInfo.cost)
//        assertEquals(2, result[0].buyerInfo.count)
//        assertEquals(18, result[0].buyerInfo.targetAge)
//    }
//}
