package com.ase.lecturerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI lecturerServiceOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Lecturer Service API")
            .description("API for managing exams, submissions, and feedback")
            .version("1.0.0"));
  }
}
