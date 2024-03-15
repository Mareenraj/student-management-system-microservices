package org.mareenraj.school.service;

import lombok.RequiredArgsConstructor;
import org.mareenraj.school.client.StudentClient;
import org.mareenraj.school.converter.SchoolConverter;
import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.exception.SchoolNotFoundException;
import org.mareenraj.school.model.School;
import org.mareenraj.school.repository.SchoolRepository;
import org.mareenraj.school.response.FullSchoolResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    private final StudentClient studentClient;

    private final SchoolConverter schoolConverter;

    public void saveSchool(SchoolDto schoolDto) {
        schoolRepository.save(schoolConverter.convertToSchool(schoolDto));
    }

    public List<SchoolDto> findAllSchool() {
        List<School> schools = schoolRepository.findAll();
        return schools.stream().map(schoolConverter::convertToSchoolDto).collect(Collectors.toList());
    }

    public FullSchoolResponse findSchoolWithStudents(Long schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);
        if (schoolOptional.isEmpty()) {
            throw new SchoolNotFoundException("School not found with id: " + schoolId);
        } else {
            School school = schoolOptional.get();
            var students = studentClient.findStudentsBySchoolId(schoolId);
            return FullSchoolResponse.builder()
                    .name(school.getName())
                    .email(school.getEmail())
                    .students(students)
                    .build();
        }
    }

    public void deleteSchoolById(Long id) {
        if (schoolRepository.existsById(id)) {
            schoolRepository.deleteById(id);
        } else {
            throw new SchoolNotFoundException("School not found with id: " + id);
        }
    }

    public SchoolDto getSchoolById(Long id) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isEmpty()) {
            throw new SchoolNotFoundException("School not found with id: " + id);
        } else {
            return schoolConverter.convertToSchoolDto(schoolOptional.get());
        }
    }

    public SchoolDto updateSchoolById(Long id, SchoolDto updatedSchoolDto) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setName(updatedSchoolDto.getName());
            school.setEmail(updatedSchoolDto.getEmail());
            school.setCountry(updatedSchoolDto.getCountry());
            school.setProvince(updatedSchoolDto.getProvince());
            school.setDistrict(updatedSchoolDto.getDistrict());
            school.setAddress(updatedSchoolDto.getAddress());
            school.setPhoneNumber(updatedSchoolDto.getPhoneNumber());
            school.setWebsiteUrl(updatedSchoolDto.getWebsiteUrl());
            School savedSchool = schoolRepository.save(school);
            return schoolConverter.convertToSchoolDto(savedSchool);
        } else {
            throw new SchoolNotFoundException("School not found with id: " + id);
        }
    }
}
