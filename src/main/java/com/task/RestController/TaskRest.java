package com.task.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.BoardsCreateDTO;
import com.task.DTO.BoardsRessponseDTO;
import com.task.DTO.BoardsUpdateDTO;
import com.task.DTO.TaskResponeDTO;
import com.task.model.Boards;
import com.task.model.Task;
import com.task.model.TaskList;
import com.task.model.User;
import com.task.service.TaskListService;
import com.task.service.TaskService;
import com.task.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Task controller", description = "Quản lý các task")
public class TaskRest {
	private final TaskService taskService;
	private final TaskListService taskListService;
	private final UserService userService;

	@GetMapping
	@Operation(summary = "Lấy danh sách Task")
	public ResponseEntity<List<TaskResponeDTO>> getListTask() {
		List<Task> task = taskService.findAll();
		List<TaskResponeDTO> result = task.stream().map(t -> mapToResponseDTO(t)).toList();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lấy chi tiết Task")
	public ResponseEntity<?> getDetailTask(@PathVariable UUID id) {
		return taskService.findById(id).map(t -> ResponseEntity.ok(mapToResponseDTO(t)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Tạo Task")
	public ResponseEntity<TaskResponeDTO> createTask(@Valid @RequestBody TaskResponeDTO dto) {
		Task task = mapToEntity(dto);
		Task saved = taskService.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(saved));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Chỉnh sửa Task")
	public ResponseEntity<TaskResponeDTO> editTask(@PathVariable UUID id, @Valid @RequestBody TaskResponeDTO dto) {
		Optional<Task> otpTask = taskService.findById(id);
		if (otpTask.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Task task = otpTask.get();
		task.setTitle(dto.getTitle());
		task.setDescription(dto.getDescription());
		task.setDealine(dto.getDealine());
		task.setTasklist(taskListService.findById(dto.getTasklistId()).orElseThrow());
		task.setAssignee(userService.findById(dto.getAssigneeId()).orElseThrow());
		Task update = taskService.save(task);
		return ResponseEntity.ok(mapToResponseDTO(update));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Xóa task")
	public ResponseEntity<?> delete(@PathVariable UUID id) {
		taskService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private Task mapToEntity(TaskResponeDTO dto) {
		// Tìm user từ UUID
		Task task = new Task();
		task.setTitle(dto.getTitle());
		task.setDescription(dto.getDescription());
		task.setDealine(dto.getDealine());
		User owner = userService.findById(dto.getAssigneeId())
				.orElseThrow(() -> new RuntimeException("Owner not found"));
		TaskList taskList = taskListService.findById(dto.getTasklistId())
				.orElseThrow(() -> new RuntimeException("TaskList not found"));
		task.setTasklist(taskList);
		task.setAssignee(owner);
		return task;

	}

	private TaskResponeDTO mapToResponseDTO(Task task) {
		TaskResponeDTO dto = new TaskResponeDTO();
		dto.setId(task.getId());
		dto.setTitle(task.getTitle());
		dto.setDescription(task.getDescription());
		dto.setDealine(task.getDealine());
		dto.setAssigneeId(task.getAssignee().getId());
		dto.setTasklistId(task.getTasklist().getId());
		return dto;
	}
}
