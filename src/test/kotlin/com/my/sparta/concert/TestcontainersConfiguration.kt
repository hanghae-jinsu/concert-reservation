package com.my.sparta.concert

import jakarta.annotation.PreDestroy
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@Configuration
class TestcontainersConfiguration {
    @PreDestroy
    fun preDestroy() {
        if (mySqlContainer.isRunning) mySqlContainer.stop()
    }

    companion object {
        @Container
        @ServiceConnection
        val mySqlContainer: MySQLContainer<*> =

            MySQLContainer(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("concert")
                .withUsername("users")
                .withPassword("password")
                .withInitScript("static/data/concert_script.sql")
                .waitingFor(Wait.forHttp("/"))                          // 가용가능 한지 기다렸다가
                .withReuse(true)
                .apply {
                            start()
                        }

        init {
            System.setProperty(
                "spring.datasource.url",
                mySqlContainer.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC",
            )
            System.setProperty("spring.datasource.username", mySqlContainer.username)
            System.setProperty("spring.datasource.password", mySqlContainer.password)
        }
    }
}
