package org.mareenraj.student.repository;

import org.mareenraj.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentBySchoolId(Long id);
}
