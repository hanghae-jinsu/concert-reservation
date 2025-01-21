package com.my.sparta.concert.aggregate.payment.adapter.inbound.controller

import com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces.PaymentRequest
import com.my.sparta.concert.aggregate.payment.adapter.inbound.interfaces.PaymentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentCommandController {

    @PostMapping("/payment")
    fun getPaymentFinishedStatus(
        @RequestBody paymentRequest: PaymentRequest
    ): PaymentResponse {

        require(paymentRequest != null) {
            "결제 flow 진행에 실패했습니다."
        }

        val madeMessage = paymentRequest.userId + ": 해당 고객님은 " + paymentRequest.paymentType + "으로 결제 완료 하셨습니다.";

        return PaymentResponse(
            httpStatus = 200,
            message = madeMessage
        )

    }
}
