package org.mareenraj.school.controller;

import lombok.RequiredArgsConstructor;
import org.mareenraj.school.service.SchoolService;
import org.mareenraj.school.model.School;
import org.mareenraj.school.response.FullSchoolResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<String> createSchool(@RequestBody School school) {
        schoolService.saveSchool(school);
        return new ResponseEntity<>("School successfully added", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        return new ResponseEntity<>(schoolService.findAllSchool(), HttpStatus.OK);
    }

    @GetMapping("/with-students/{school-id}")
    public ResponseEntity<FullSchoolResponse> findSchoolWithStudents(@PathVariable("school-id") Long schoolId) {
        return ResponseEntity.ok(schoolService.findSchoolWithStudents(schoolId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchoolById(@PathVariable Long id){
        schoolService.deleteSchoolById(id);
        return new ResponseEntity<>("School deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable("id") Long id){
        School school = schoolService.getSchoolById(id);
        if(school != null){
            return new ResponseEntity<>(school, HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<School> updateSchoolById(@PathVariable Long id, @RequestBody School school){
        School updatedSchool = schoolService.updateSchoolById(id,school);
        if(updatedSchool != null){
            return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.valueOf("Can't update this school"));
        }
    }
}
