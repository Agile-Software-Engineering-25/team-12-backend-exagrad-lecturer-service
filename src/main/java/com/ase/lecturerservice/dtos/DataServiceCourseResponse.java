package com.ase.lecturerservice.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DataServiceCourseResponse {
  private boolean success;
  private int statusCode;
  private String status;
  private String message;
  private String timestamp;
  private String endpoint;
  private List<DataServiceCourseResponse.DataServiceCourseDto> data;
  private String error;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DataServiceCourseDto {
    private String id;
    private List<Teacher> teachers;
    private TemplateDto template;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Teacher {
      @JsonProperty("external_id")
      private String externalId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemplateDto {
      private String name;
      private String code;
      private Boolean elective;
      private Integer plannedSemester;
    }
  }
}
