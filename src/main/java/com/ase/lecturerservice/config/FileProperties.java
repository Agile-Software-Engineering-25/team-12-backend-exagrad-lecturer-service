package com.ase.lecturerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "app.file")
public class FileProperties {
  private long maxSize; // in bytes
  private String allowedTypes;
}
