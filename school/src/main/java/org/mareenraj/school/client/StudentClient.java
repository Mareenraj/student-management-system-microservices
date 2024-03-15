package org.mareenraj.school.client;

import org.mareenraj.school.response.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student-service", url= "${application.config.students-url}")
public interface StudentClient {
    @GetMapping("/school/{school-id}")
    List<Student> findStudentsBySchoolId(@PathVariable("school-id") Long schoolId);
}
