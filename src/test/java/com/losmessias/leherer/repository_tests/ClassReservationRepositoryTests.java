package com.losmessias.leherer.repository_tests;

import com.losmessias.leherer.domain.ClassReservation;
import com.losmessias.leherer.domain.Professor;
import com.losmessias.leherer.domain.Student;
import com.losmessias.leherer.domain.Subject;
import com.losmessias.leherer.domain.enumeration.ReservationStatus;
import com.losmessias.leherer.repository.ClassReservationRepository;
import com.losmessias.leherer.repository.ProfessorRepository;
import com.losmessias.leherer.repository.StudentRepository;
import com.losmessias.leherer.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
public class ClassReservationRepositoryTests {

    @Autowired
    private ClassReservationRepository classReservationRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;

    private ClassReservation classReservation;
    private Professor professor;
    private Subject subject;
    private Student student;

    @BeforeEach
    public void setupData() {
        professor = Professor.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        subject = Subject.builder()
                .name("Math")
                .build();
        student = Student.builder()
                .firstName("Jane")
                .lastName("Doe")
                .build();

        professorRepository.save(professor);
        subjectRepository.save(subject);
        studentRepository.save(student);
        classReservation = ClassReservation.builder()
                .professor(professor)
                .subject(subject)
                .student(student)
                .date(LocalDate.of(2023, 1, 1))
                .startingHour(LocalTime.of(9, 0))
                .endingHour(LocalTime.of(10, 0))
                .duration(1.0)
                .price(100)
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should save a class reservation")
    public void shouldSaveAClassReservation() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should find by professorId")
    public void shouldFindByProfessorId() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.findByProfessorId(professor.getId()).size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should find by studentId")
    public void shouldFindByStudentId() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.findByStudentId(student.getId()).size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should find by subjectId")
    public void shouldFindBySubjectId() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.findBySubjectId(subject.getId()).size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should make simple daily summary")
    public void shouldMakeDailySummary() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.getProfessorDailySummaryByDay(classReservation.getDate()).size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Should make daily summary with multiple subjects")
    public void shouldMakeDailySummaryWithMultipleSubjects() {
        Subject subject2 = Subject.builder()
                .name("Portuguese")
                .build();
        subjectRepository.save(subject2);
        ClassReservation classReservation2 = ClassReservation.builder()
                .professor(professor)
                .subject(subject2)
                .student(student)
                .date(LocalDate.of(2023, 1, 1))
                .startingHour(LocalTime.of(10, 0))
                .endingHour(LocalTime.of(11, 0))
                .duration(1.0)
                .price(100)
                .status(ReservationStatus.CONFIRMED)
                .build();
        classReservationRepository.save(classReservation);
        classReservationRepository.save(classReservation2);
        assertEquals(2, classReservationRepository.getProfessorDailySummaryByDay(classReservation.getDate()).size());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Find by professor and subject id")
    public void shouldFindByProfessorAndSubject() {
        classReservationRepository.save(classReservation);
        assertEquals(1, classReservationRepository.findByProfessorAndSubject(professor, subject).size());
    }

}
