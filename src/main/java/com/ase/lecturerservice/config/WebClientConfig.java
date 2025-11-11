package com.ase.lecturerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.components.TokenManager;

@Configuration
public class WebClientConfig {
  @Bean
  public WebClient tokenWebClient() {
    return WebClient.builder().build();
  }

  @Bean
  public WebClient webClient(TokenManager tokenManager) {
    return WebClient.builder()
        .filter(addAuthHeaderFilter(tokenManager))
        .build();
  }

  private ExchangeFilterFunction addAuthHeaderFilter(TokenManager tokenManager) {
    return (ClientRequest request, ExchangeFunction next) ->
        tokenManager.getAccessToken()
            .flatMap(token -> {
              ClientRequest newRequest = ClientRequest.from(request)
                  .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                  .build();
              return next.exchange(newRequest);
            });
  }
}
