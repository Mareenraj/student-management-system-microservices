package org.mareenraj.student.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mareenraj.student.converter.StudentConverter;
import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.exception.StudentNotFoundException;
import org.mareenraj.student.model.Student;
import org.mareenraj.student.repository.StudentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentConverter studentConverter;

    @Mock
    private StudentRepository studentRepository;

    private Student student;

    private StudentDto studentDto;

    @Test
    void saveStudent() {
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);

        when(studentConverter.covertToStudent(studentDto)).thenReturn(student);

        studentService.saveStudent(studentDto);

        verify(studentRepository, times(1)).save(student);
        verify(studentConverter, times(1)).covertToStudent(studentDto);
    }

    @Test
    void findAllStudents() {
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        List<Student> students = new ArrayList<>();
        students.add(student);
        List<StudentDto> expectedStudentDtos = new ArrayList<>();
        expectedStudentDtos.add(studentDto);

        when(studentRepository.findAll()).thenReturn(students);
        when(studentConverter.covertToStudentDto(student)).thenReturn(studentDto);

        List<StudentDto> result = studentService.findAllStudents();

        verify(studentRepository, times(1)).findAll();
        verify(studentConverter, times(1)).covertToStudentDto(student);
        assertEquals(result, expectedStudentDtos);
    }

    @Test
    void findStudentsBySchoolId() {
        Long schoolId = 1L;
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        List<Student> students = new ArrayList<>();
        students.add(student);
        List<StudentDto> expectedStudentDtos = new ArrayList<>();
        expectedStudentDtos.add(studentDto);


        when(studentRepository.findStudentBySchoolId(schoolId)).thenReturn(students);
        when(studentConverter.covertToStudentDto(student)).thenReturn(studentDto);

        List<StudentDto> result = studentService.findStudentsBySchoolId(schoolId);

        verify(studentRepository, times(1)).findStudentBySchoolId(schoolId);
        verify(studentConverter, times(1)).covertToStudentDto(student);
        assertEquals(result.size(), 1);
        assertEquals(result, expectedStudentDtos);
    }

    @Test
    void deleteStudentSuccess() {
        Long id = 1L;

        when(studentRepository.existsById(id)).thenReturn(true);

        studentService.deleteStudent(id);

        verify(studentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteStudentNotFound() {
        Long id = 1L;

        when(studentRepository.existsById(id)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(id));
        verify(studentRepository, never()).deleteById(id);
    }

    @Test
    void getStudentById() {
        Long id = 1L;
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentConverter.covertToStudentDto(student)).thenReturn(studentDto);

        StudentDto result = studentService.getStudentById(id);

        assertEquals(studentDto, result);
        verify(studentConverter, times(1)).covertToStudentDto(student);
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void ifStudentIdNotFound() {
        Long id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(id));
        assertEquals("Student not found with id: " + id, exception.getMessage());
    }

    @Test
    void updateStudentById() {
        // Given
        Long id = 1L;
        StudentDto updatedStudentDto = new StudentDto(1L, "Jane", "Smith", 30, "janesmith@example.com", "California", "Female", "grade 11", "9876543210", 2L);
        Student student = new Student(1L, "John", "Doe", 25, "johndoe@example.com", "New York", "Male", "grade 10", "1234567890", 1L);


        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentConverter.covertToStudentDto(any(Student.class))).thenReturn(updatedStudentDto);

        // When
        StudentDto result = studentService.updateStudentById(id, updatedStudentDto);

        // Then
        assertEquals(updatedStudentDto, result);
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentConverter, times(1)).covertToStudentDto(student);
    }

    @Test
    void updateStudentByIdIfStudentNotFound() {
        Long id = 1L;
        StudentDto studentDto = new StudentDto(1L, "Jane", "Smith", 30, "janesmith@example.com", "California", "Female", "grade 11", "9876543210", 2L);

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudentById(id, studentDto));
    }
}