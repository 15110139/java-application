package com.main.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString()
public class CreateUserRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
}
