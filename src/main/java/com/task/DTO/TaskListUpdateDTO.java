package com.task.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskListUpdateDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String position;

	@NotNull
	private UUID boardId;
}
