package org.mareenraj.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mareenraj.school.dto.SchoolDto;
import org.mareenraj.school.response.FullSchoolResponse;
import org.mareenraj.school.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school")
@RequiredArgsConstructor
@Validated
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<String> createSchool(@RequestBody @Valid SchoolDto schoolDto) {
        schoolService.saveSchool(schoolDto);
        return new ResponseEntity<>("School successfully added", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDto>> getAllSchools() {
        return new ResponseEntity<>(schoolService.findAllSchool(), HttpStatus.OK);
    }

    @GetMapping("/with-students/{school-id}")
    public ResponseEntity<FullSchoolResponse> findSchoolWithStudents(@PathVariable("school-id") Long schoolId) {
        return ResponseEntity.ok(schoolService.findSchoolWithStudents(schoolId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchoolById(@PathVariable Long id) {
        schoolService.deleteSchoolById(id);
        return new ResponseEntity<>("School deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getSchoolById(@PathVariable("id") Long id) {
        SchoolDto schoolDto = schoolService.getSchoolById(id);
        return new ResponseEntity<>(schoolDto, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchoolById(@PathVariable Long id, @RequestBody @Valid SchoolDto schoolDto) {
        SchoolDto updatedSchool = schoolService.updateSchoolById(id, schoolDto);
        return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
    }
}
