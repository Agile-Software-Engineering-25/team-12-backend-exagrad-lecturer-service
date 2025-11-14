package com.ase.lecturerservice.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationServiceNotificationPayload {
  private List<String> users;
  private String title;
  private String message;
  private NotificationType notificationType;
  private NotifyType notifyType;

  public enum NotificationType {
    Info,
    Warning,
    Congratulation,
    None
  }

  public enum NotifyType {
    Mail,
    UI,
    All
  }
}
