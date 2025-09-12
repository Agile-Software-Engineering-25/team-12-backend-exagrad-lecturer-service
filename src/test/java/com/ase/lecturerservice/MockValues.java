package com.ase.lecturerservice;

import lombok.Getter;

public class MockValues {

  @Getter
  public enum UuidMocks {
    EXAM_UUID("550e8400-e29b-41d4-a716-446655440000"),
    EXAM_UUID2("550e8400-e29b-41d4-a716-446655440001"),
    EXAM_UUID3("550e8400-e29b-41d4-a716-446655440002"),
    EXAM_UUID4("550e8400-e29b-41d4-a716-446655440003"),
    EXAM_UUID5("550e8400-e29b-41d4-a716-446655440004"),

    STUDENT_UUID("d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f"),
    STUDENT_UUID2("f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a"),
    STUDENT_UUID3("7283a092-2b64-4bfa-bf92-4242448b740a"),
    STUDENT_UUID4("a9f5d8b5-2632-42b5-8520-1db4010fc80d"),
    STUDENT_UUID5("be7f4234-cd28-4b29-9b09-5d1a38d3c67a"),

    GRADE_UUID("ea3f2b67-5ed0-4d89-bc2c-28533a210ae2"),
    GRADE_UUID2("27d211f8-e45e-4f5a-b264-e7b4f51e8f95"),
    GRADE_UUID3("cc28b1f6-3b5b-44e1-963f-0793b742a6d4"),

    LECTURER_UUID("12345678-62hj-jhj2-h23j-901234567890"),
    LECTURER_UUID2("3f8a9c12-7b4e-4d21-9c8a-2e6b7d9f1a23"),
    LECTURER_UUID3("c1d2e3f4-5a6b-4c7d-8e9f-0a1b2c3d4e5f"),
    LECTURER_UUID4("a0b1c2d3-e4f5-4a67-8b9c-d0e1f2a3b4c5"),
    LECTURER_UUID5("9d8c7b6a-5e4f-4a3b-9c8d-7e6f5a4b3c2d"),
    LECTURER_UUID6("f0e1d2c3-b4a5-49c6-8d7e-6f5a4b3c2d1e");



    private final String value;

    UuidMocks(String value) {
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
    DATE_DAY(25);

    private final int value;

    IntMocks(int value) {
      this.value = value;
    }
  }
}
