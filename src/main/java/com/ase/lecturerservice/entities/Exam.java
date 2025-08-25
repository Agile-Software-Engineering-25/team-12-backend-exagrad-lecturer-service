package com.ase.lecturerservice.entities;

import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @Id
    @UuidGenerator
    protected UUID id;

    @Column(name = "name")
    protected String name;

    @Column(name = "grade")
    protected int grade;

    @Column(name = "average_grade")
    protected int averageGrade;

    @Column(name = "total_points")
    protected int totalPoints;

    @Column(name = "achieved_points")
    protected int achievedPoints;

    @Column(name = "exam_type")
    protected String examType;

    @Column(name = "date")
    protected LocalDate date;

    @Column(name = "time")
    protected int time;

    @Column(name = "allowed_resources")
    protected String allowedResources;

    @Column(name = "attempt")
    protected int attempt;

    @Column(name = "etcs")
    protected int etcs;

    @Column(name = "room")
    protected String room;

    @Column(name = "module")
    protected String module;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lecturer_id")
    protected Lecturer lecturer;

    @ManyToMany(mappedBy = "assignedExams", fetch = FetchType.LAZY)
    protected List<Student> assignedStudents;
}
