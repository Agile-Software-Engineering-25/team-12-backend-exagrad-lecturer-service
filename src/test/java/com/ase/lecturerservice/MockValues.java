package com.ase.lecturerservice;

import java.util.UUID;
import lombok.Getter;

public class MockValues {

  @Getter
  public enum UuidMocks {
    EXAM_UUID(UUID.randomUUID()),
    STUDENT_UUID(UUID.randomUUID()),
    GRADE_UUID(UUID.randomUUID());

    private final UUID value;

    UuidMocks(UUID value) {
      this.value = value;
    }
  }

  @Getter
  public enum FloatMocks {
    GRADE(1.3f),
    AVERAGE_GRADE(2.0f);

    private final float value;

    FloatMocks(float value) {
      this.value = value;
    }
  }

  @Getter
  public enum IntMocks {
    SUBMISSIONS(0),
    TOTAL_POINTS(100),
    ACHIEVED_POINTS(95),
    TIME_SECONDS(5400),
    TIME_MIN(90),
    ATTEMPT(1),
    ETCS(5),
    DATE_YEAR(2015),
    DATE_MONTH(10),
    DATE_DAY(12);

    private final int value;

    IntMocks(int value) {
      this.value = value;
    }
  }
}
