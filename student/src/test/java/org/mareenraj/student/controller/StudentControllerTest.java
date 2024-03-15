//package org.mareenraj.student.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mareenraj.student.dto.StudentDto;
//import org.mareenraj.student.service.StudentService;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(controllers = StudentController.class)
//@ExtendWith(MockitoExtension.class)
//class StudentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private StudentService studentService;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Test
//    void createStudent() {
//
//    }
//
//    @Test
//    void getAllStudents() {
//    }
//
//    @Test
//    void findStudentsBySchoolId() {
//    }
//
//    @Test
//    void deleteStudentById() {
//    }
//
//    @Test
//    void getStudentById() throws Exception {
//        Long studentId = 1L;
//        StudentDto studentDto = new StudentDto(1L, "John", "Doe", 25, "XNpZv@example.com", "New York", "Male", "grade 10", "1234567890", 1L);
//
//        when(studentService.getStudentById(studentId)).thenReturn(studentDto);
//
//        mockMvc.perform(get("/api/v1/student/{id}", studentId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.studentId").value(studentId))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.age").value(25))
//                .andExpect(jsonPath("$.email").value("XNpZv@example.com"))
//                .andExpect(jsonPath("$.location").value("New York"))
//                .andExpect(jsonPath("$.gender").value("Male"))
//                .andExpect(jsonPath("$.grade").value("grade 10"))
//                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
//                .andExpect(jsonPath("$.schoolId").value(1L));
//    }
//
//    @Test
//    void updateStudentById() {
//
//    }
//
//}