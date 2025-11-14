package com.ase.lecturerservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.ase.lecturerservice.dtos.NotificationServiceNotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final WebClient webClient;

  @Value("${app.apis.notification-service.baseurl}")
  private String notificationServiceBaseUrl;

  public void sendNotification(NotificationServiceNotificationPayload payload) {
    webClient.post()
        .uri(notificationServiceBaseUrl + "/notifications")
        .bodyValue(payload)
        .retrieve()
        .toBodilessEntity()
        .block();
  }
}
