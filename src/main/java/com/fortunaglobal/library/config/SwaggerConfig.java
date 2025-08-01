package com.fortunaglobal.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FortunaGlobal Library API")
                        .version("1.0")
                        .description("RESTful API for Library Management System")
                        .contact(new Contact()
                                .name("FortunaGlobal Tech Support")
                                .email("tech@fortunaglobal.com")
                                .url("https://fortunaglobal.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}