package com.hermann.cepservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Consulta de CEP com Logs")
                        .version("1.0")
                        .description("API para consulta de CEP e registro de logs de acesso.")
                        .contact(new Contact()
                                .name("Douglas Hermann")
                                .email("00000@email.com")
                                .url("https://www.linkedin.com/in/douglas-hermann-de-araujo/")
                        )
                );
    }
}
