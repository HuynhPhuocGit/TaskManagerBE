package com.task.model;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskList {
	@Id
	@UuidGenerator
	@Column(name = "id", nullable = false, updatable = false, length = 36)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID id;

	private String name;

	private String position;

	@ManyToOne
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private Boards board;

}
