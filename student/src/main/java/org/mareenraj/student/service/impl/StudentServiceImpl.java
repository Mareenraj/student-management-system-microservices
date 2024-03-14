package org.mareenraj.student.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mareenraj.student.converter.StudentConverter;
import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.exception.StudentNotFoundException;
import org.mareenraj.student.model.Student;
import org.mareenraj.student.repository.StudentRepository;
import org.mareenraj.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;

    @Transactional
    public void saveStudent(StudentDto studentDto) {
        studentRepository.save(studentConverter.covertToStudent(studentDto));
    }

    public List<StudentDto> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentConverter::covertToStudentDto).collect(Collectors.toList());
    }

    public List<StudentDto> findStudentsBySchoolId(Long schoolId) {
        List<Student> students = studentRepository.findStudentBySchoolId(schoolId);
        return students.stream().map(studentConverter::covertToStudentDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public StudentDto getStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty()) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        return studentConverter.covertToStudentDto(studentOptional.get());
    }

    @Transactional
    public StudentDto updateStudentById(Long id, StudentDto updatedStudentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        Student student = optionalStudent.get();
        student.setFirstName(updatedStudentDto.getFirstName());
        student.setLastName(updatedStudentDto.getLastName());
        student.setAge(updatedStudentDto.getAge());
        student.setEmail(updatedStudentDto.getEmail());
        student.setLocation(updatedStudentDto.getLocation());
        student.setGender(updatedStudentDto.getGender());
        student.setGrade(updatedStudentDto.getGrade());
        student.setPhoneNumber(updatedStudentDto.getPhoneNumber());
        student.setSchoolId(updatedStudentDto.getSchoolId());
        return studentConverter.covertToStudentDto(studentRepository.save(student));
    }
}
