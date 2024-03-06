package org.mareenraj.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>("Student successfully added", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.findAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/school/{school-id}")
    public ResponseEntity<List<Student>> findStudentsBySchoolId(@PathVariable("school-id") Long schoolId) {
        return new ResponseEntity<>(studentService.findStudentsBySchoolId(schoolId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
        Student student = studentService.getStudentById(id);
        if(student != null){
            return new ResponseEntity<>(student, HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long id, @RequestBody Student student){
        Student updatedStudent = studentService.updateStudentById(id,student);
        if(updatedStudent != null){
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.valueOf("Can't update this student"));
        }
    }
}
