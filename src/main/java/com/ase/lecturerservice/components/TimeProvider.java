package com.ase.lecturerservice.components;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimeProvider {

  private final ZoneId appZoneId;

  public ZonedDateTime now() {
    return ZonedDateTime.now(appZoneId);
  }
}
