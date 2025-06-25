package com.task.DTO;

import java.util.UUID;

import com.task.model.Boards;
import com.task.model.User;

import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TaskListResponeDTO {
	private UUID id;

	private String name;

	private String position;


	private UUID boardId;


}
