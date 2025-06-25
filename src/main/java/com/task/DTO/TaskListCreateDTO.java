package com.task.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskListCreateDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String position;

	@NotNull
	private UUID boardId;
}
