package com.ase.lecturerservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.MockValues;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.dtos.FeedbackResponse;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.mappers.FeedbackMapper;
import com.ase.lecturerservice.services.FeedbackDocumentService;
import com.ase.lecturerservice.services.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

  private static final LocalDate DATE =
      LocalDate.of(
          MockValues.IntMocks.DATE_YEAR.getValue(),
          MockValues.IntMocks.DATE_MONTH.getValue(),
          MockValues.IntMocks.DATE_DAY.getValue());
  private static final String TEST_FILE_PATH = "testfiles/test.pdf";
  static LocalDate date =
      LocalDate.of(
          com.ase.lecturerservice.mockvalues.MockValues.IntMocks.DATE_YEAR.getValue(),
          com.ase.lecturerservice.mockvalues.MockValues.IntMocks.DATE_MONTH.getValue(),
          com.ase.lecturerservice.mockvalues.MockValues.IntMocks.DATE_DAY.getValue());
  static List<FileReference> fileReferencesList =
      List.of(
          FileReference.builder()
              .fileUuid(UUID.randomUUID())
              .fileName("dummy_file")
              .build(),
          FileReference.builder()
              .fileUuid(UUID.randomUUID())
              .fileName("dummy_file2")
              .build());
  private static Feedback feedback;
  private final FeedbackMapper feedbackMapper = new FeedbackMapper();
  @Autowired
  private ResourceLoader resourceLoader;
  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private FeedbackService feedbackService;
  @MockitoBean
  private FeedbackDocumentService feedbackDocumentService;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeAll
  public static void setup() {
    feedback =
        Feedback.builder()
            .uuid(MockValues.UuidMocks.GRADE_UUID.getValue())
            .gradedAt(DATE)
            .lecturerUuid(UUID.randomUUID().toString())
            .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
            .submissionUuid(UUID.randomUUID().toString())
            .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
            .comment("Excellent work on the assignment.")
            .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
            .grade(MockValues.FloatMocks.GRADE.getValue())
            .build();
  }

  @Test
  void saveFeedbackShouldSave() throws Exception {
    Feedback feedback =
        Feedback.builder()
            .uuid(
                com.ase.lecturerservice.mockvalues.MockValues.UuidMocks.GRADE_UUID2
                    .getValue())
            .gradedAt(date)
            .lecturerUuid(UUID.randomUUID().toString())
            .studentUuid(
                com.ase.lecturerservice.mockvalues.MockValues.UuidMocks
                    .STUDENT_UUID2
                    .getValue())
            .submissionUuid(UUID.randomUUID().toString())
            .examUuid(
                com.ase.lecturerservice.mockvalues.MockValues.UuidMocks.EXAM_UUID
                    .getValue())
            .comment("Great effort! Check feedback in files.")
            .points(
                com.ase.lecturerservice.mockvalues.MockValues.IntMocks
                    .ACHIEVED_POINTS
                    .getValue())
            .grade(
                com.ase.lecturerservice.mockvalues.MockValues.FloatMocks.GRADE
                    .getValue())
            .build();

    // 2. JSON-Daten serialisieren
    String feedbackJson = objectMapper.writeValueAsString(feedback);

    // 3. Testdatei laden und als MockMultipartFile vorbereiten
    org.springframework.core.io.Resource resource =
        resourceLoader.getResource("classpath:" + TEST_FILE_PATH);
    byte[] fileContent = FileCopyUtils.copyToByteArray(resource.getInputStream());

    MockMultipartFile testFile =
        new MockMultipartFile(
            "files", // WICHTIG: Muss dem Namen des @RequestParam in deinem Controller
            // entsprechen (z.B. @RequestParam("files"))
            "testfile.pdf", // Dateiname
            "application/pdf", // Content-Type
            fileContent // Dateibyte-Inhalt
        );

    // 4. JSON-Teil als MockMultipartFile vorbereiten
    // WICHTIG: Muss dem Namen des @RequestPart in deinem Controller entsprechen (z.B.
    // @RequestPart("feedbackData"))
    MockMultipartFile feedbackData =
        new MockMultipartFile(
            "feedbackData",
            "", // Dateiname ist hier leer
            "application/json",
            feedbackJson.getBytes(StandardCharsets.UTF_8));

    doNothing()
        .when(feedbackService)
        .saveFeedback(
            any(FeedbackRequest.class),
            any(MultipartFile[].class) // Verwende den korrekten Array-Typ
        );
    mockMvc.perform(
            post("/api/v1/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(feedbackJson))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated());

    mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/v1/feedback")
                .file(feedbackData) // Fügt den JSON-Teil hinzu
                .file(testFile) // Fügt die eigentliche Datei hinzu
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated());
  }

  @Test
  void getFeedbackForLecturerShouldReturnListofFeedback() throws Exception {
    List<FeedbackResponse> feedbackList = List.of(feedbackMapper.toResponse(feedback));

    when(feedbackService.getFeedbackForLecturer("Tom")).thenReturn(feedbackList);

    mockMvc.perform(
            get("/api/v1/feedback/for-lecturer/Tom")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].gradedAt").value(DATE.toString()))
        .andExpect(
            jsonPath("$[0].studentUuid")
                .value(MockValues.UuidMocks.STUDENT_UUID.getValue()))
        .andExpect(
            jsonPath("$[0].examUuid").value(MockValues.UuidMocks.EXAM_UUID.getValue()))
        .andExpect(jsonPath("$[0].comment").value("Excellent work on the assignment."))
        .andExpect(jsonPath("$[0].grade").value(MockValues.FloatMocks.GRADE.getValue()))
        .andExpect(
            jsonPath("$[0].points")
                .value(MockValues.IntMocks.ACHIEVED_POINTS.getValue()));
  }
}
