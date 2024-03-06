package org.mareenraj.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    public List<Student> findStudentsBySchoolId(Long schoolId) {
        return studentRepository.findStudentBySchoolId(schoolId);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public Student getStudentById(Long id){
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    public Student updateStudentById(Long id, Student updatedStudent){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()){
            Student student = studentOptional.get();
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setAge(updatedStudent.getAge());
            student.setSchoolId(updatedStudent.getSchoolId());
            studentRepository.save(student);
            return student;
        }
        return null;
    }
}
