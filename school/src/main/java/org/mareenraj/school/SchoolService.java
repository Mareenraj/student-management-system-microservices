package org.mareenraj.school;

import lombok.RequiredArgsConstructor;
import org.mareenraj.school.client.StudentClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    private final StudentClient studentClient;

    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    public List<School> findAllSchool() {
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolWithStudents(Long schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(
                School.builder()
                        .name("Not Found")
                        .email("Not Found")
                        .build()
        );
        var students = studentClient.findStudentsBySchoolId(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }

    public void deleteSchoolById(Long id){
        schoolRepository.deleteById(id);
    }

    public School getSchoolById(Long id){
        Optional<School> schoolOptional = schoolRepository.findById(id);
        return schoolOptional.orElse(null);
    }

    public School updateSchoolById(Long id, School updatedSchool){
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if(schoolOptional.isPresent()){
            School school = schoolOptional.get();
            school.setName(updatedSchool.getName());
            school.setEmail(updatedSchool.getEmail());
            schoolRepository.save(school);
            return school;
        }
        return null;
    }
}
