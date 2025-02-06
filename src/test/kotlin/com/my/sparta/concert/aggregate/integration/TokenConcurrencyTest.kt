package com.my.sparta.concert.aggregate.integration

import com.my.sparta.concert.aggregate.user.adapter.outbound.persistence.repository.TokenQueueRedisRepository
import com.my.sparta.concert.aggregate.user.application.domain.service.GenerateTokenService
import com.my.sparta.concert.common.util.TokenUtilService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenConcurrencyTest(
    @Autowired private val dataSource: DataSource,
    @Autowired private val tokenUtilService: TokenUtilService,
    @Autowired private val generateTokenService: GenerateTokenService,
    @Autowired private val tokenQueueRedisRepository: TokenQueueRedisRepository,
) {


    @BeforeEach
    fun setUp(testInfo: TestInfo) {
        runSqlScript("static/data/user_script.sql")

        (0..50).forEach { i ->
            generateTokenService.generateToken("user" + (i + 1));
        }

    }

    @Test
    fun `test for real token`() {

        val activeToken = tokenQueueRedisRepository.findAllTokenId();
        val waitingToken = tokenQueueRedisRepository.findAllWaitingToken();

        assertThat(waitingToken.size).isEqualTo(1);
        assertThat(activeToken.size).isEqualTo(50);

    }

    private fun runSqlScript(scriptPath: String) {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource(scriptPath))
        // 스크립트 실행
        DatabasePopulatorUtils.execute(populator, dataSource)
    }
}


