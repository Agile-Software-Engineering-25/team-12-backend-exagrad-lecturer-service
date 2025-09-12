package com.ase.lecturerservice.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.Submission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionService {

  private final ExamService examService;

  public List<Submission> getSubmissionsForExam(String examId) {
    return DummyData.SUBMISSIONS.stream().filter(submission -> submission.getExamId().equals(examId)).toList();
  }

  public List<Submission> getSubmissionsForStudent(String studentId) {
    return DummyData.SUBMISSIONS.stream().filter(submission -> submission.getStudentId().equals(studentId)).toList();
  }

  public List<Submission> getAllAccessibleSubmissionsForLecturer(String lecturerUuid) {
    Set<String> examsOfLecturer = examService.getExamsByLecturer(lecturerUuid).stream().map(Exam::getUuid).collect(Collectors.toSet());
    return DummyData.SUBMISSIONS.stream()
        .filter(submission -> examsOfLecturer.contains(submission.getExamId()))
        .collect(Collectors.toList());
  }

}
