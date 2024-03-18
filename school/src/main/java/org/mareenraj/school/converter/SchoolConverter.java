package org.mareenraj.school.converter;

import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.model.School;
import org.springframework.stereotype.Service;

@Service
public class SchoolConverter {

    public School convertToSchool(SchoolDto schoolDto) {
        if (schoolDto == null) {
            throw new NullPointerException("schoolDto can't be null!");
        }
        return new School(schoolDto.getId(), schoolDto.getName(), schoolDto.getEmail(), schoolDto.getCountry(), schoolDto.getProvince(), schoolDto.getDistrict(), schoolDto.getAddress(), schoolDto.getPhoneNumber(), schoolDto.getWebsiteUrl());
    }

    public SchoolDto convertToSchoolDto(School school) {
        if (school == null) {
            throw new NullPointerException("school can't be null!");
        }
        return new SchoolDto(school.getId(), school.getName(), school.getEmail(), school.getCountry(), school.getProvince(), school.getDistrict(), school.getAddress(), school.getPhoneNumber(), school.getWebsiteUrl());
    }
}
