package org.mareenraj.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.response.FullSchoolResponse;
import org.mareenraj.school.response.Student;
import org.mareenraj.school.service.SchoolService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SchoolController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {
    private static final String SCHOOL_API_URL = "/api/v1/school";
    SchoolDto schoolDto;
    List<SchoolDto> schoolDtoList;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SchoolService schoolService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        schoolDto = new SchoolDto(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "https://www.mareenweb.com/");
    }

    @Test
    void createSchool() throws Exception {
        doNothing().when(schoolService).saveSchool(schoolDto);

        mockMvc.perform(post(SCHOOL_API_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(schoolDto)))

                .andExpect(status().isCreated())
                .andExpect(content().string("School successfully added"));
    }

    @Test
    void getAllSchools() throws Exception {
        schoolDtoList = new ArrayList<>();
        schoolDtoList.add(schoolDto);

        when(schoolService.findAllSchool()).thenReturn(schoolDtoList);

        mockMvc.perform(get(SCHOOL_API_URL))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(schoolDto.getName()))
                .andExpect(jsonPath("$[0].email").value(schoolDto.getEmail()))
                .andExpect(jsonPath("$[0].country").value(schoolDto.getCountry()))
                .andExpect(jsonPath("$[0].province").value(schoolDto.getProvince()))
                .andExpect(jsonPath("$[0].district").value(schoolDto.getDistrict()))
                .andExpect(jsonPath("$[0].address").value(schoolDto.getAddress()))
                .andExpect(jsonPath("$[0].phoneNumber").value(schoolDto.getPhoneNumber()))
                .andExpect(jsonPath("$[0].websiteUrl").value(schoolDto.getWebsiteUrl()));
    }

    @Test
    void findSchoolWithStudents() throws Exception {
        Long schoolId = 1L;
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", "Doe", 20, "john.doe@example.com", "New York", "Male", "10th Grade", "1234567890", 1L));
        students.add(new Student(2L, "Jane", "Doe", 19, "jane.doe@example.com", "New York", "Female", "10th Grade", "0987654321", 1L));
        FullSchoolResponse fullSchoolResponse = new FullSchoolResponse("KHC", "robinmareen@gamil.com", students);

        when(schoolService.findSchoolWithStudents(schoolId)).thenReturn(fullSchoolResponse);

        mockMvc.perform(get(SCHOOL_API_URL + "/with-students/{school-id}", schoolId))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students.length()").value(2))
                .andExpect(jsonPath("$.name").value(fullSchoolResponse.getName()))
                .andExpect(jsonPath("$.email").value(fullSchoolResponse.getEmail()))
                .andExpect(jsonPath("$.students[1].email").value(fullSchoolResponse.getStudents().get(1).getEmail()));
    }

    @Test
    void deleteSchoolById() throws Exception {
        Long schoolId = 1L;

        doNothing().when(schoolService).deleteSchoolById(schoolId);
        mockMvc.perform(delete(SCHOOL_API_URL + "/{id}", schoolId))

                .andExpect(status().isOk())
                .andExpect(content().string("School deleted successfully!"));
        verify(schoolService, times(1)).deleteSchoolById(schoolId);
    }

    @Test
    void getSchoolById() throws Exception {
        Long schoolId = 1L;

        when(schoolService.getSchoolById(schoolId)).thenReturn(schoolDto);

        mockMvc.perform(get(SCHOOL_API_URL + "/{id}", schoolId))

                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(schoolDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolDto.getAddress()))
                .andExpect(jsonPath("$.country").value(schoolDto.getCountry()))
                .andExpect(jsonPath("$.email").value(schoolDto.getEmail()))
                .andExpect(jsonPath("$.district").value(schoolDto.getDistrict()))
                .andExpect(jsonPath("$.province").value(schoolDto.getProvince()))
                .andExpect(jsonPath("$.phoneNumber").value(schoolDto.getPhoneNumber()))
                .andExpect(jsonPath("$.websiteUrl").value(schoolDto.getWebsiteUrl()));
    }

    @Test
    void updateSchoolById() throws Exception {
        Long schoolId = 1L;

        SchoolDto updatedSchoolDto = new SchoolDto(2L, "Jaffna hindu college", "kirusaanth@gmail.com", "Srilanka", "North", "Jaffna", "pandathripuu", "0779828389", "https://www.kiruweb.com/\"");

        when(schoolService.updateSchoolById(schoolId, updatedSchoolDto)).thenReturn(updatedSchoolDto);

        mockMvc.perform(put(SCHOOL_API_URL + "/{id}", schoolId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedSchoolDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedSchoolDto.getName()))
                .andExpect(jsonPath("$.websiteUrl").value(updatedSchoolDto.getWebsiteUrl()))
                .andExpect(jsonPath("$.phoneNumber").value(updatedSchoolDto.getPhoneNumber()))
                .andExpect(jsonPath("$.province").value(updatedSchoolDto.getProvince()))
                .andExpect(jsonPath("$.district").value(updatedSchoolDto.getDistrict()))
                .andExpect(jsonPath("$.email").value(updatedSchoolDto.getEmail()))
                .andExpect(jsonPath("$.country").value(updatedSchoolDto.getCountry()))
                .andExpect(jsonPath("$.address").value(updatedSchoolDto.getAddress()))
                .andExpect(jsonPath("$.id").value(updatedSchoolDto.getId()));
    }
}