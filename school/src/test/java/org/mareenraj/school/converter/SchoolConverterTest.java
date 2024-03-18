package org.mareenraj.school.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.model.School;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class SchoolConverterTest {

    SchoolConverter schoolConverter;

    @BeforeEach
    void setUp() {
        schoolConverter = new SchoolConverter();
    }

    @Test
    void convertToSchool() {
        SchoolDto schoolDto = new SchoolDto(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");

        School school = schoolConverter.convertToSchool(schoolDto);

        assertEquals(schoolDto.getId(), school.getId());
        assertEquals(schoolDto.getName(), school.getName());
        assertEquals(schoolDto.getEmail(), school.getEmail());
        assertEquals(schoolDto.getCountry(), school.getCountry());
        assertEquals(schoolDto.getProvince(), school.getProvince());
        assertEquals(schoolDto.getDistrict(), school.getDistrict());
        assertEquals(schoolDto.getAddress(), school.getAddress());
        assertEquals(schoolDto.getPhoneNumber(), school.getPhoneNumber());
        assertEquals(schoolDto.getWebsiteUrl(), school.getWebsiteUrl());
    }

    @Test
    void convertToSchoolDto() {
        School school = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");

        SchoolDto schoolDto = schoolConverter.convertToSchoolDto(school);

        assertEquals(schoolDto.getId(), school.getId());
        assertEquals(schoolDto.getName(), school.getName());
        assertEquals(schoolDto.getEmail(), school.getEmail());
        assertEquals(schoolDto.getCountry(), school.getCountry());
        assertEquals(schoolDto.getProvince(), school.getProvince());
        assertEquals(schoolDto.getDistrict(), school.getDistrict());
        assertEquals(schoolDto.getAddress(), school.getAddress());
        assertEquals(schoolDto.getPhoneNumber(), school.getPhoneNumber());
        assertEquals(schoolDto.getWebsiteUrl(), school.getWebsiteUrl());
    }

    @Test
    void convertToSchool_ifSchoolDtoIsNull() {
        var exp = Assertions.assertThrows(NullPointerException.class, () -> {
            schoolConverter.convertToSchool(null);
        });
        assertEquals("schoolDto can't be null!", exp.getMessage());
    }

    @Test
    void convertToSchool_ifStudentDtoIsNull() {
        var exp = Assertions.assertThrows(NullPointerException.class, () -> {
            schoolConverter.convertToSchoolDto(null);
        });
        assertEquals("school can't be null!", exp.getMessage());
    }
}
