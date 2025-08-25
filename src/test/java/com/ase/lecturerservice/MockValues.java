package com.ase.lecturerservice;

import lombok.Getter;

@Getter
public enum MockValues {
  GRADE(1),
  SUBMISSIONS(1),
  AVERAGE_GRADE(2),
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

  MockValues(int value) {
    this.value = value;
  }

}

