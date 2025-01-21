package com.my.sparta.concert.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info =
        io.swagger.v3.oas.annotations.info.Info(
            title = "concert reservation server",
            description = "Concert Reservation API Documentation",
            version = "v1.0.0",
        ),
    servers =
        arrayOf(
            Server(
                url = "localhost:8080",
                description = "local API",
            ),
        ),
    tags = [
        Tag(name = "concert API", description = "concert API"),
        Tag(name = "money", description = "money API"),
        Tag(name = "user", description = "user API"),
    ],
)
@Configuration
class OpenApiConfig
