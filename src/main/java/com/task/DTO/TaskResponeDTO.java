package com.task.DTO;

import java.security.Timestamp;
import java.util.UUID;

import lombok.Data;

@Data
public class TaskResponeDTO {

	private UUID id;
	private String title;
	private String description;
	private Timestamp dealine;

	private UUID tasklistId;
	
	private UUID assigneeId;
	
}
