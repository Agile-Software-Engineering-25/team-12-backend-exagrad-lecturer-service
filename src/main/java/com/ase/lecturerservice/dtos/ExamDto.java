package com.ase.lecturerservice.dtos;

import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.user.Student;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
public class ExamDto {
    @NotNull private String uuid;

    @NotNull private String name;

    @NotNull private LocalDate date;

    @NotNull private String module;

    @NotNull private ExamType examType;

    @NotNull private List<Student> assignedStudents;

    @NotNull private int time;

    @NotNull private int totalPoints;

    @NotNull private boolean fileUploadRequired;
}
