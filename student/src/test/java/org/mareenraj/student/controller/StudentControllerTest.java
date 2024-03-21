package org.mareenraj.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mareenraj.student.dto.StudentDto;
import org.mareenraj.student.service.StudentService;
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

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {
    private static final String STUDENT_API_URL = "/api/v1/student";
    StudentDto studentDto;
    List<StudentDto> studentDtoList;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentDto = new StudentDto(1L,
                "John",
                "Doe",
                20,
                "john.doe@example.com",
                "New York",
                "Male",
                "A",
                "1234567890",
                1L);
    }

    @Test
    void createStudent() throws Exception {
        doNothing().when(studentService).saveStudent(studentDto);

        mockMvc.perform(post(STUDENT_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto))
                )

                .andExpect(status().isCreated())
                .andExpect(content().string("Student successfully created"));
    }

    @Test
    void getAllStudents() throws Exception {
        studentDtoList = new ArrayList<>();
        studentDtoList.add(studentDto);

        when(studentService.findAllStudents()).thenReturn(studentDtoList);

        mockMvc.perform(get(STUDENT_API_URL))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].studentId").value(studentDto.getStudentId()))
                .andExpect(jsonPath("$[0].firstName").value(studentDto.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(studentDto.getLastName()))
                .andExpect(jsonPath("$[0].age").value(studentDto.getAge()))
                .andExpect(jsonPath("$[0].gender").value(studentDto.getGender()))
                .andExpect(jsonPath("$[0].grade").value(studentDto.getGrade()))
                .andExpect(jsonPath("$[0].location").value(studentDto.getLocation()))
                .andExpect(jsonPath("$[0].phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(jsonPath("$[0].email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$[0].schoolId").value(studentDto.getSchoolId()));
    }

    @Test
    void findStudentsBySchoolId() throws Exception {
        Long schoolId = 1L;
        studentDtoList = new ArrayList<>();
        studentDtoList.add(studentDto);

        when(studentService.findStudentsBySchoolId(schoolId)).thenReturn(studentDtoList);

        mockMvc.perform(get(STUDENT_API_URL + "/school/{school-id}", schoolId))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].studentId").value(studentDto.getStudentId()))
                .andExpect(jsonPath("$[0].firstName").value(studentDto.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(studentDto.getLastName()))
                .andExpect(jsonPath("$[0].age").value(studentDto.getAge()))
                .andExpect(jsonPath("$[0].gender").value(studentDto.getGender()))
                .andExpect(jsonPath("$[0].grade").value(studentDto.getGrade()))
                .andExpect(jsonPath("$[0].location").value(studentDto.getLocation()))
                .andExpect(jsonPath("$[0].phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(jsonPath("$[0].email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$[0].schoolId").value(studentDto.getSchoolId()));
    }

    @Test
    void deleteStudentById() throws Exception {
        Long id = 1L;

        doNothing().when(studentService).deleteStudent(id);

        mockMvc.perform(delete(STUDENT_API_URL + "/{id}", id))

                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted successfully!"));
        verify(studentService, times(1)).deleteStudent(id);
    }

    @Test
    void getStudentById() throws Exception {
        Long id = 1L;

        when(studentService.getStudentById(id)).thenReturn(studentDto);

        mockMvc.perform(get(STUDENT_API_URL + "/{id}", id))

                .andExpect(status().isFound())
                .andExpect(jsonPath("$.studentId").value(id))
                .andExpect(jsonPath("$.firstName").value(studentDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentDto.getLastName()))
                .andExpect(jsonPath("$.age").value(studentDto.getAge()))
                .andExpect(jsonPath("$.gender").value(studentDto.getGender()))
                .andExpect(jsonPath("$.grade").value(studentDto.getGrade()))
                .andExpect(jsonPath("$.location").value(studentDto.getLocation()))
                .andExpect(jsonPath("$.phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$.schoolId").value(studentDto.getSchoolId()));
    }

    @Test
    void updateStudentById() throws Exception {
        Long id = 1L;

        when(studentService.updateStudentById(id, studentDto)).thenReturn(studentDto);

        mockMvc.perform(put(STUDENT_API_URL + "/{id}", id, studentDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(id))
                .andExpect(jsonPath("$.firstName").value(studentDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentDto.getLastName()))
                .andExpect(jsonPath("$.age").value(studentDto.getAge()))
                .andExpect(jsonPath("$.gender").value(studentDto.getGender()))
                .andExpect(jsonPath("$.grade").value(studentDto.getGrade()))
                .andExpect(jsonPath("$.location").value(studentDto.getLocation()))
                .andExpect(jsonPath("$.phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$.schoolId").value(studentDto.getSchoolId()));
    }
}