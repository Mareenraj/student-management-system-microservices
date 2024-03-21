package org.mareenraj.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mareenraj.school.client.StudentClient;
import org.mareenraj.school.converter.SchoolConverter;
import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.exception.SchoolNotFoundException;
import org.mareenraj.school.model.School;
import org.mareenraj.school.repository.SchoolRepository;
import org.mareenraj.school.response.FullSchoolResponse;
import org.mareenraj.school.response.Student;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {
    @InjectMocks
    private SchoolService schoolService;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private SchoolConverter schoolConverter;
    @Mock
    private StudentClient studentClient;

    @Test
    void saveSchool() {
        SchoolDto schoolDto = new SchoolDto(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");
        School school = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");

        when(schoolConverter.convertToSchool(schoolDto)).thenReturn(school);

        schoolService.saveSchool(schoolDto);

        verify(schoolRepository, times(1)).save(school);
        verify(schoolConverter, times(1)).convertToSchool(schoolDto);
    }

    @Test
    void findAllSchool() {
        School school1 = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");
        School school2 = new School(2L, "Jaffna hindu college", "kirusaanth@gmail.com", "Srilanka", "North", "Jaffna", "pandathripuu", "0779828389", "kiruWeb.com");
        SchoolDto schoolDto1 = new SchoolDto(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");
        SchoolDto schoolDto2 = new SchoolDto(2L, "Jaffna hindu college", "kirusaanth@gmail.com", "Srilanka", "North", "Jaffna", "pandathripuu", "0779828389", "kiruWeb.com");

        when(schoolRepository.findAll()).thenReturn(Arrays.asList(school1, school2));
        when(schoolConverter.convertToSchoolDto(school1)).thenReturn(schoolDto1);
        when(schoolConverter.convertToSchoolDto(school2)).thenReturn(schoolDto2);

        List<SchoolDto> result = schoolService.findAllSchool();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), schoolDto1);
        assertEquals(result.get(1), schoolDto2);
        verify(schoolRepository, times(1)).findAll();
        verify(schoolConverter, times(2)).convertToSchoolDto(any(School.class));
    }

    @Test
    void findSchoolWithStudents() {
        Long schoolId = 1L;
        School school = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", "Doe", 20, "john.doe@example.com", "New York", "Male", "10th Grade", "1234567890", 1L));
        students.add(new Student(2L, "Jane", "Doe", 19, "jane.doe@example.com", "New York", "Female", "10th Grade", "0987654321", 1L));
        FullSchoolResponse expectedFullSchoolResponse = new FullSchoolResponse(school.getName(), school.getEmail(), students);

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(studentClient.findStudentsBySchoolId(schoolId)).thenReturn(students);

        FullSchoolResponse result = schoolService.findSchoolWithStudents(schoolId);

        assertEquals(result.getName(), expectedFullSchoolResponse.getName());
        assertEquals(result.getEmail(), expectedFullSchoolResponse.getEmail());
        assertEquals(result.getStudents(), expectedFullSchoolResponse.getStudents());
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(studentClient, times(1)).findStudentsBySchoolId(schoolId);
    }

    @Test
    void findSchoolWithStudentsWhenSchoolOptionalEqualToEmpty(){
        Long schoolId = 1L;
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());
        assertThrows(SchoolNotFoundException.class,()->{
            schoolService.findSchoolWithStudents(schoolId);
        });
    }

    @Test
    void deleteSchoolById() {
        Long schoolId = 1L;

        when(schoolRepository.existsById(schoolId)).thenReturn(true);

        schoolService.deleteSchoolById(schoolId);

        verify(schoolRepository, times(1)).deleteById(schoolId);
    }

    @Test
    void deleteSchoolNonExistId() {
        Long schoolId = 1L;

        when(schoolRepository.existsById(schoolId)).thenReturn(false);

        assertThrows(SchoolNotFoundException.class, () -> schoolService.deleteSchoolById(schoolId));
        verify(schoolRepository, never()).deleteById(schoolId);
    }

    @Test
    void getSchoolById_ExistingSchoolId_ReturnsSchoolDto() {
        // Arrange
        Long schoolId = 1L;
        School school = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");
        SchoolDto schoolDto = new SchoolDto(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");

        // Mock behavior of schoolRepository.findById() to return an existing school
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(schoolConverter.convertToSchoolDto(school)).thenReturn(schoolDto);

        // Act
        SchoolDto result = schoolService.getSchoolById(schoolId);

        // Assert
        assertEquals(schoolDto, result);

        // Verify interactions
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolConverter, times(1)).convertToSchoolDto(school);
    }

    @Test
    void getSchoolById_NonExistingSchoolId_ThrowsSchoolNotFoundException() {
        // Arrange
        Long schoolId = 1L;

        // Mock behavior of schoolRepository.findById() to return Optional.empty()
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SchoolNotFoundException.class, () -> schoolService.getSchoolById(schoolId));

        // Verify interactions
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolConverter, never()).convertToSchoolDto(any());
    }

    @Test
    void updateExistingSchoolById_ExistingSchoolId_ReturnsUpdatedSchoolDto() {
        // Arrange
        Long schoolId = 1L;
        SchoolDto updatedSchoolDto = new SchoolDto(2L, "Jaffna hindu college", "kirusaanth@gmail.com", "Srilanka", "North", "Jaffna", "pandathripuu", "0779828389", "kiruWeb.com");
        School existingSchool = new School(1L, "kokuvil hindu college", "robinmmareen@gmail.com", "Srilanka", "North", "Jaffna", "Navaly", "0771028389", "mareenWeb.com");

        // Mock behavior of schoolRepository.findById() to return an existing school
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(existingSchool));

        // Mock behavior of schoolRepository.save() to return the updated school
        when(schoolRepository.save(existingSchool)).thenReturn(existingSchool);

        // Mock behavior of schoolConverter.convertToSchoolDto() to return the updated schoolDto
        when(schoolConverter.convertToSchoolDto(existingSchool)).thenReturn(updatedSchoolDto);

        // Act
        SchoolDto result = schoolService.updateSchoolById(schoolId, updatedSchoolDto);

        // Assert
        assertEquals(updatedSchoolDto, result);

        // Verify interactions
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolRepository, times(1)).save(existingSchool);
        verify(schoolConverter, times(1)).convertToSchoolDto(existingSchool);
    }


    @Test
    void updateNonExistingSchoolById() {
        // Arrange
        Long schoolId = 1L;
        SchoolDto updatedSchoolDto = new SchoolDto(2L, "Jaffna hindu college", "kirusaanth@gmail.com", "Srilanka", "North", "Jaffna", "pandathripuu", "0779828389", "kiruWeb.com");

        // Mock behavior of schoolRepository.findById() to return Optional.empty()
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SchoolNotFoundException.class, () -> schoolService.updateSchoolById(schoolId, updatedSchoolDto));

        // Verify interactions
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolRepository, never()).save(any());
        verify(schoolConverter, never()).convertToSchoolDto(any());
    }
}