package org.mareenraj.student.service;

import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.model.Student;
import java.util.List;


public interface StudentService {
    void saveStudent(StudentDto studentDto);

    List<StudentDto> findAllStudents();

    List<StudentDto> findStudentsBySchoolId(Long schoolId);

    void deleteStudent(Long id);

    StudentDto getStudentById(Long id);

    StudentDto updateStudentById(Long id, StudentDto updatedStudentDto);
}
