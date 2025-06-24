package com.task.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class BoardsRessponseDTO {

	private UUID id;

	private String name;

	private UUID ownerId;

}
