package com.sushil.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    @NotEmpty(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @Email(message = "Please enter a valid email")
    @NotEmpty(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    private String email;

    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
