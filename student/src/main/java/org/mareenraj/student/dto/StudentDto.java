package org.mareenraj.student.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class StudentDto {

    private Long studentId;

    @NotBlank(message = "First Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name should only contain alphabets")
    @Size(min = 2, max = 30, message = "First Name should be between 2 and 30 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name should only contain alphabets")
    @Size(min = 2, max = 30, message = "Last Name should be between 2 and 30 characters")
    private String lastName;

    @NotNull(message = "Age cannot be null")
    @Min(value = 5, message = "Age should be greater than or equal to 5")
    @Max(value = 100, message = "Age should be less than or equal to 100")
    private Integer age;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "^(Male|Female)$", message = "Gender should be either 'Male' or 'Female'")
    private String gender;

    @NotBlank(message = "Grade cannot be blank")
    private String grade;

    @NotBlank(message = "Phone Number cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone Number should only contain digits")
    private String phoneNumber;

    @NotNull(message = "School Id cannot be null")
    private Long schoolId;

}
