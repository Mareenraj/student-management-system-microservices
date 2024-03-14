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

import java.util.Arrays;
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
        when(studentRepository.save(student)).thenReturn(student);

        studentService.saveStudent(studentDto);

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void findAllStudents() {
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        List<Student> students = Arrays.asList(student);
        List<StudentDto> studentDtos = Arrays.asList(studentDto);

        when(studentRepository.findAll()).thenReturn(students);
        when(studentConverter.covertToStudentDto(student)).thenReturn(studentDto);

        List<StudentDto> studentDtoList = studentService.findAllStudents();

        verify(studentRepository, times(1)).findAll();
        verify(studentConverter, times(1)).covertToStudentDto(student);
        assertEquals(studentDtoList, studentDtos);
    }

    @Test
    void findStudentsBySchoolId() {
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        List<Student> students = Arrays.asList(student);
        List<StudentDto> studentDtos = Arrays.asList(studentDto);
        Long schoolId = 1L;

        when(studentRepository.findStudentBySchoolId(schoolId)).thenReturn(students);
        when(studentConverter.covertToStudentDto(student)).thenReturn(studentDto);

        List<StudentDto> studentDtoList = studentService.findStudentsBySchoolId(schoolId);

        verify(studentRepository, times(1)).findStudentBySchoolId(schoolId);
        verify(studentConverter, times(1)).covertToStudentDto(student);
        assertEquals(studentDtoList.size(), 1);
        assertEquals(studentDtos, studentDtoList);
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
        student = new Student(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        Long id = 1L;
        Optional<Student> studentOptional = Optional.of(student);

        when(studentRepository.findById(id)).thenReturn(studentOptional);
        when(studentConverter.covertToStudentDto(studentOptional.get())).thenReturn(studentDto);

        StudentDto result = studentService.getStudentById(id);

        assertEquals(studentDto, result);
    }

    @Test
    void ifStudentIdNotFound() {
        Long id = 1L;
        Optional<Student> studentOptional = Optional.empty();

        when(studentRepository.findById(id)).thenReturn(studentOptional);

        Throwable exception = assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(id));
        assertEquals("Student not found with id: " + id, exception.getMessage());
    }

    @Test
    void updateStudentById() {
        // Given
        Long id = 1L;
        StudentDto updatedStudentDto = new StudentDto(1L, "Jane", "Smith", 30, "janesmith@example.com", "California", "Female", "grade 11", "9876543210", 2L);
        Student student = new Student(1L, "John", "Doe", 25, "johndoe@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
        Optional<Student> optionalStudent = Optional.of(student);

        when(studentRepository.findById(id)).thenReturn(optionalStudent);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentConverter.covertToStudentDto(any(Student.class))).thenReturn(updatedStudentDto);

        // When
        StudentDto result = studentService.updateStudentById(id, updatedStudentDto);

        // Then
        assertEquals(updatedStudentDto, result);
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
}