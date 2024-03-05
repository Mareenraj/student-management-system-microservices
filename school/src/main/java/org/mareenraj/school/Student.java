package org.mareenraj.school;

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
}
