package com.ase.lecturerservice.components;

import java.time.Instant;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenManager {
  private static final long TOKEN_REFRESH_BUFFER = 30;
  @Qualifier("tokenWebClient")
  private final WebClient tokenWebClient;
  private String accessToken;
  private Instant expirationTime = Instant.EPOCH;
  @Value("${app.apis.auth.url}")
  private String tokenUrl;
  @Value("${app.apis.auth.client-id}")
  private String clientId;
  @Value("${app.apis.auth.client-secret}")
  private String clientSecret;

  public synchronized Mono<String> getAccessToken() {
    if (accessToken == null || Instant.now().isAfter(
        expirationTime.minusSeconds(TOKEN_REFRESH_BUFFER))) {
      return refreshToken();
    }
    return Mono.just(accessToken);
  }

  private Mono<String> refreshToken() {
    return tokenWebClient.post()
        .uri(tokenUrl)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .bodyValue("grant_type=client_credentials"
            + "&client_id=" + clientId
            + "&client_secret=" + clientSecret)
        .retrieve()
        .bodyToMono(Map.class)
        .map(response -> {
          this.accessToken = (String) response.get("access_token");
          int expiresIn = (int) response.get("expires_in");
          this.expirationTime = Instant.now().plusSeconds(expiresIn);
          return this.accessToken;
        });
  }
}
