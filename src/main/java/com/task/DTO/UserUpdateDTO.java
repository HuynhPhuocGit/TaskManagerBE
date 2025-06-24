package com.task.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {
	@NotBlank(message = "username không được để trống")
	private String username;

	@Email
	@NotBlank(message = "Email không được để trống")
	private String email;

	private String role;
}
