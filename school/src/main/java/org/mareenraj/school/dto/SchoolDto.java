package org.mareenraj.school.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class SchoolDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name should only contain alphabets")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotBlank(message = "Province cannot be blank")
    private String province;

    @NotBlank(message = "District cannot be blank")
    private String district;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be valid")
    private String phoneNumber;

    @URL(message = "Website URL should be valid")
    private String websiteUrl;

}
