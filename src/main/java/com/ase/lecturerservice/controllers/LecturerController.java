package com.ase.lecturerservice.controllers;

import com.ase.lecturerservice.dtos.ExamDto;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.services.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ase.lecturerservice.controllers.BaseController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH + "/lecturer")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @GetMapping("/exams")
    public ResponseEntity<List<ExamDto>> getExams(
            @RequestParam String lecturer) throws IllegalArgumentException {
        List<Exam> exams = lecturerService.getExamsByLecturer(lecturer);
        return ResponseEntity.ok(lecturerService.convertToExamDto(exams));
    }
}
