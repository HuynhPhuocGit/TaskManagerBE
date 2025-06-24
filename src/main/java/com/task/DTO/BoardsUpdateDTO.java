package com.task.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardsUpdateDTO {

	@NotBlank(message = "Tên bảng không được để trống")
	private String name;

	@NotNull(message = "ID người tạo không được để trống")
	private UUID ownerId;
	
}
