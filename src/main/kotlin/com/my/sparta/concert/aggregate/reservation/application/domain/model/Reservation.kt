package com.my.sparta.concert.aggregate.reservation.application.domain.model

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.concert.application.domain.model.ConcertSeat
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.BuyerInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ChargeInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.ConcertInfo
import com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject.SeatInfo
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import java.util.*

class Reservation(
    var reservationId: String,
    var chargeInfo: ChargeInfo,
    var concertInfo: ConcertInfo,
    var buyerInfo: BuyerInfo,
) {

    companion object {

        fun createReservation(
            concert: Concert,
            userInfo: Users,
            concertSeat: ConcertSeat,
            command: ConcertReservationCommand,
            payment: Payment
        ): Reservation {
            return Reservation(
                reservationId = UUID.randomUUID().toString(),
                chargeInfo =
                ChargeInfo(
                    payment.paymentId,
                    payment.paymentType,
                ),
                concertInfo =
                ConcertInfo(
                    concert.concertId,
                    concert.concertName,
                    SeatInfo(concertSeat.id),
                    concert.runningTime,
                ),
                buyerInfo =
                BuyerInfo(
                    userInfo.userId,
                    concert.cost * command.count,
                    command.count,
                    concert.targetAge,
                ),
            )
        }
    }
}
