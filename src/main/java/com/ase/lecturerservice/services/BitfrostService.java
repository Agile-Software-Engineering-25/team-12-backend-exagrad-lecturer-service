package com.ase.lecturerservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BitfrostService {
  private final WebClient webClient = WebClient.create();

  @Value("${app.apis.bitfrost.tag}")
  private String lecturerServiceTag;

  @Value("${app.apis.bitfrost.secret}")
  private String secret;

  private static final String BASE_URL = "https://bitfrost.sau-portal.de/api/v1/messages/publish";
  public void sendRequest(String topicName, Object payload) {
    String url = String.format("%s/%s/%s", BASE_URL, lecturerServiceTag, topicName);

    try {
      webClient.post()
          .uri(url)
          .header(HttpHeaders.AUTHORIZATION, "Executor " + lecturerServiceTag + ":" + secret)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(payload)
          .retrieve()
          .toBodilessEntity()
          .block();
    }
    catch (WebClientResponseException ex) {
      throw new RuntimeException(
          "Failed to send message to topic '" + topicName + "': "
              + ex.getStatusCode() + " " + ex.getResponseBodyAsString(),
          ex
      );
    }
    catch (Exception ex) {
      throw new RuntimeException("Unexpected error while sending message to topic '"
          + topicName + "'", ex);
    }
  }
}
