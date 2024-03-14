package org.mareenraj.student.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@Validated
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentDto studentDto) {
        studentService.saveStudent(studentDto);
        return new ResponseEntity<>("Student successfully created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.findAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/school/{school-id}")
    public ResponseEntity<List<StudentDto>> findStudentsBySchoolId(@PathVariable("school-id") Long schoolId) {
        return new ResponseEntity<>(studentService.findStudentsBySchoolId(schoolId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(studentService.updateStudentById(id, studentDto), HttpStatus.OK);
    }
}
