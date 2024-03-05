package org.mareenraj.school;

import lombok.RequiredArgsConstructor;
import org.mareenraj.school.client.StudentClient;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
