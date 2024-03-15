package org.mareenraj.school.response;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String location;
    private String gender;
    private String grade;
    private String phoneNumber;
    private Long schoolId;
}
