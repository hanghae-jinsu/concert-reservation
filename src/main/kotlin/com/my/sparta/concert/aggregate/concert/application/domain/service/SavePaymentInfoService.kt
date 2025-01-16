package com.my.sparta.concert.aggregate.concert.application.domain.service

import com.my.sparta.concert.aggregate.concert.application.domain.model.Concert
import com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces.PaymentRequest
import com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces.PaymentResponse
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.SavePaymentInfoUseCase
import com.my.sparta.concert.aggregate.reservation.application.port.inbound.command.ConcertReservationCommand
import com.my.sparta.concert.aggregate.reservation.application.port.outbound.SavePaymentHistoryPort
import com.my.sparta.concert.aggregate.user.application.domain.model.Payment
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PayingTransaction
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@RequiredArgsConstructor
class SavePaymentInfoService(
    private val savePaymentHistoryPort: SavePaymentHistoryPort,
    private val restTemplate: RestTemplate
) : SavePaymentInfoUseCase {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun savePayment(userInfo: Users, concert: Concert, command: ConcertReservationCommand): Payment {

        val totalPrice = concert.cost * command.count

//        val response = requestPayment(userInfo.userId)

//        require(response.message != null) {
//                 "결제가 실패하였습니다."
//        }
//
//        logger.info("$response.message")

        val payment = Payment("", userInfo.userId, command.paymentType, totalPrice, PayingTransaction.PAYMENT)
        return savePaymentHistoryPort.savePaymentInfo(payment)

    }

//    private fun requestPayment(userId: String): PaymentResponse {
//        val url = "https://localhost:8080/payment"
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//
//        val paymentRequest = PaymentRequest(
//            userId = userId,
//            paymentType = PaymentType.CARD,
//        )
//
//        val request = HttpEntity(paymentRequest, headers)
//
//        return restTemplate.postForObject(url, request, PaymentResponse::class.java)
//            ?: throw RuntimeException("결제 요청에 실패했습니다.")
//    }
}
