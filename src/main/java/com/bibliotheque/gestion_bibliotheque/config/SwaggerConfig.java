package com.bibliotheque.gestion_bibliotheque.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion de Bibliothèque")
                        .version("1.0.0")
                        .description("API REST pour gérer une bibliothèque selon les principes de Clean Architecture")
                        .contact(new Contact()
                                .name("Équipe ESIEA")
                                .email("ramanadane@esiea.et.fr")));
    }
}
