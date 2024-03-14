package org.mareenraj.student.converter;

import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.model.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentConverter {

    public StudentDto covertToStudentDto(Student student) {
        if (student == null) {
            throw new NullPointerException("student can't be null!");
        }
        return new StudentDto(student.getStudentId(), student.getFirstName(), student.getLastName(), student.getAge(), student.getEmail(), student.getLocation(), student.getGender(), student.getGrade(), student.getPhoneNumber(), student.getSchoolId());
    }

    public Student covertToStudent(StudentDto studentDto) {
        if (studentDto == null) {
            throw new NullPointerException("studentDto can't be null!");
        }
        return new Student(studentDto.getStudentId(), studentDto.getFirstName(), studentDto.getLastName(), studentDto.getAge(), studentDto.getEmail(), studentDto.getLocation(), studentDto.getGender(), studentDto.getGrade(), studentDto.getPhoneNumber(), studentDto.getSchoolId());
    }
}
