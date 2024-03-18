package org.mareenraj.student.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class StudentConverterTest {
    StudentConverter studentConverter;

    @BeforeEach
    void setUp() {
        studentConverter = new StudentConverter();
    }

    @Test
    void covertToStudentDto() {
        Student student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);

        StudentDto studentDto = studentConverter.covertToStudentDto(student);

        assertEquals(student.getStudentId(), studentDto.getStudentId());
        assertEquals(student.getFirstName(), studentDto.getFirstName());
        assertEquals(student.getLastName(), studentDto.getLastName());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(student.getEmail(), studentDto.getEmail());
        assertEquals(student.getLocation(), studentDto.getLocation());
        assertEquals(student.getGender(), studentDto.getGender());
        assertEquals(student.getGrade(), studentDto.getGrade());
        assertEquals(student.getPhoneNumber(), studentDto.getPhoneNumber());
        assertEquals(student.getSchoolId(), studentDto.getSchoolId());
    }

    @Test
    void covertToStudent() {
        StudentDto studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);

        Student student = studentConverter.covertToStudent(studentDto);

        assertEquals(student.getStudentId(), studentDto.getStudentId());
        assertEquals(student.getFirstName(), studentDto.getFirstName());
        assertEquals(student.getLastName(), studentDto.getLastName());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(student.getEmail(), studentDto.getEmail());
        assertEquals(student.getLocation(), studentDto.getLocation());
        assertEquals(student.getGender(), studentDto.getGender());
        assertEquals(student.getGrade(), studentDto.getGrade());
        assertEquals(student.getPhoneNumber(), studentDto.getPhoneNumber());
        assertEquals(student.getSchoolId(), studentDto.getSchoolId());
    }

    @Test
    void covertToStudent_IfStudentDtoEqualToNull() {
        assertThrows(NullPointerException.class, () -> studentConverter.covertToStudentDto(null));
    }

    @Test
    void covertToStudentDto_IfStudentEqualToNull() {
        assertThrows(NullPointerException.class, () -> studentConverter.covertToStudent(null));
    }
}