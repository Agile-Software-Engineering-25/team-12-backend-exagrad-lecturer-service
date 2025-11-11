package com.ase.lecturerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
  private String feedbackDocumentsBucket;
}
