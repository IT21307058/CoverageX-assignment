package com.taskmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name="Bhanuka Lakshitha Dayananda",
                        email="bhanukalakshitha22@gmail.com"
                ),
                description = "Create a simple Spring Boot application that provides a RESTful API for managing a list of tasks.",
                title = "Cypso Labs",
                version = "1.0"

        )
)
public class OpenApiConfig {
}
