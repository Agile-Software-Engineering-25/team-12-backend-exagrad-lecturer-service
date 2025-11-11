package com.ase.lecturerservice.components;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenRefresher {
  private final TokenManager tokenManager;

  @Scheduled(fixedRate = 4 * 60 * 1000)
  public void refreshToken() {
    tokenManager.getAccessToken()
        .onErrorResume(e -> Mono.empty())
        .subscribe();
  }
}
