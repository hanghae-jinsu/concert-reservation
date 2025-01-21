package com.my.sparta.concert.aggregate.user.adapter.inbound.controller

import com.my.sparta.concert.aggregate.user.adapter.inbound.interfaces.response.UserWalletInfoResponse
import com.my.sparta.concert.aggregate.user.adapter.inbound.mapper.UserWebMapper
import com.my.sparta.concert.aggregate.user.application.domain.model.Users
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.PaymentType
import com.my.sparta.concert.aggregate.user.application.domain.valueobject.Wallet
import com.my.sparta.concert.aggregate.user.application.port.inbound.GenerateTokenUseCase
import com.my.sparta.concert.aggregate.user.application.port.outbound.LoadUserInfoPort
import com.my.sparta.concert.common.util.TokenUtilService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.*
import jakarta.persistence.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [UserQueryController::class])
class UserQueryControllerTest(
    @Autowired val mockMvc: MockMvc,

    // MockBean을 사용하여 Spring 컨텍스트에서 의존성을 모킹
    @MockBean val generateTokenUseCase: GenerateTokenUseCase,
    @MockBean val loadUserInfoPort: LoadUserInfoPort,
    @MockBean val userWebMapper: UserWebMapper,
    @MockBean val tokenUtilService: TokenUtilService,
) : BehaviorSpec({

    extensions(SpringExtension)

    Given("유효한 userId로 잔고 정보를 조회할 때") {
        val userId = "user12345"

        // 모킹할 사용자 정보 객체 생성
        val mockUserInfo = Users(
            userId = userId,
            username = "user",
            age = 20,
            wallet = Wallet(PaymentType.CARD, 5000.0)
        )

        // 모킹할 UserWalletInfoResponse 생성
        val mockUserWalletInfoResponse = UserWalletInfoResponse(
            userId = userId,
            money = 5000.0
        )

        // 의존성 모킹 설정
        every { loadUserInfoPort.getUserInfoById(userId) } returns mockUserInfo
        every { userWebMapper.mapToResponse(mockUserInfo) } returns mockUserWalletInfoResponse

        When("GET /user/money/{userId} 요청을 보내면") {
            val response = mockMvc.get("/user/money/{userId}", userId) {
                contentType = MediaType.APPLICATION_JSON
            }

            Then("응답 상태는 200 OK이고, 잔고 정보가 반환되어야 한다") {
                response.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.userId") { value(userId) }
                    jsonPath("$.remainingBalance") { value(50000.0) }
                }
            }
        }
    }

    Given("존재하지 않는 userId로 잔고 정보를 조회할 때") {
        val invalidUserId = "invalidUserId"

        // 의존성 모킹 설정: NotFoundException 발생
        every { loadUserInfoPort.getUserInfoById(invalidUserId) } throws EntityNotFoundException("User not found")

        When("GET /user/money/{userId} 요청을 보내면") {
            val response = mockMvc.get("/user/money/{userId}", invalidUserId) {
                contentType = MediaType.APPLICATION_JSON
            }

            Then("응답 상태는 404 Not Found를 반환해야 한다") {
                response.andExpect {
                    status { isNotFound() }
                }
            }
        }
    }


})
