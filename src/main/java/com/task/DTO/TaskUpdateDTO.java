package com.task.DTO;

import java.security.Timestamp;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskUpdateDTO {

	@NotBlank(message = "title không được để trống")
	private String title;
	private String description;
	private Timestamp dealine;

	@NotNull(message = " không được để trống ID của tasklist")
	private UUID tasklistId;
	
	private UUID assigneeId;
}
