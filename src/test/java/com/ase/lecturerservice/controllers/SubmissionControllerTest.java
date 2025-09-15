package com.ase.lecturerservice.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.entities.Submission;
import com.ase.lecturerservice.services.SubmissionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubmissionController.class)
public class SubmissionControllerTest {
  private static final String LECTURER_UUID = "lecturer-uuid-123";
  private static final String STUDENT_UUID = MockValues.UuidMocks.STUDENT_UUID.getValue();
  private static final String EXAM_UUID = MockValues.UuidMocks.EXAM_UUID.getValue();
  private static final String SUBMISSION_DATE = "2025-09-01T10:30:00Z";
  private static final String FILENAME = "submission1.pdf";
  private static final String DOWNLOAD_LINK = "https://example.com/submission1.pdf";

  private static Submission submission;
  private static List<Submission> submissionList;

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SubmissionService submissionService;

  @BeforeAll
  public static void setup() {
    FileReference fileReference = FileReference.builder()
        .fileUuid(UUID.randomUUID().toString())
        .filename(FILENAME)
        .downloadLink(DOWNLOAD_LINK)
        .build();

    submission = Submission.builder()
        .id(UUID.randomUUID().toString())
        .examId(EXAM_UUID)
        .studentId(STUDENT_UUID)
        .submissionDate(SUBMISSION_DATE)
        .fileUpload(fileReference)
        .build();

    submissionList = List.of(submission);
  }

  @Test
  void getRelevantSubmissionsShouldReturnSubmissionsForLecturer() throws Exception {
    when(submissionService.getAllAccessibleSubmissionsForLecturer(LECTURER_UUID))
        .thenReturn(submissionList);

    mockMvc.perform(get("/api/v1/submissions/for-lecturer/{lecturerUuid}", LECTURER_UUID)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].examId").value(EXAM_UUID))
        .andExpect(jsonPath("$[0].studentId").value(STUDENT_UUID))
        .andExpect(jsonPath("$[0].submissionDate").value(SUBMISSION_DATE))
        .andExpect(jsonPath("$[0].fileUpload.filename").value(FILENAME))
        .andExpect(jsonPath("$[0].fileUpload.downloadLink").value(DOWNLOAD_LINK));
  }

  @Test
  void getRelevantSubmissionsShouldReturnEmptyListWhenNoSubmissions() throws Exception {
    when(submissionService.getAllAccessibleSubmissionsForLecturer(LECTURER_UUID))
        .thenReturn(List.of());

    mockMvc.perform(get("/api/v1/submissions/for-lecturer/{lecturerUuid}", LECTURER_UUID)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void getRelevantSubmissionsShouldHandleInvalidLecturerUuid() throws Exception {
    String invalidLecturerUuid = "invalid-uuid";
    when(submissionService.getAllAccessibleSubmissionsForLecturer(invalidLecturerUuid))
        .thenReturn(List.of());

    mockMvc.perform(get("/api/v1/submissions/for-lecturer/{lecturerUuid}", invalidLecturerUuid)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());
  }
}
