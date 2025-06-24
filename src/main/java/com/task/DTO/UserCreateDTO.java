package com.task.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank(message = "username không được để trống")
    private String username;

    @Email
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min=8 ,message = "Password phải nhiều hơn 8 kí tự")
    private String password;

    private String role;
}
