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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.TaskListCreateDTO;
import com.task.DTO.TaskListResponeDTO;
import com.task.DTO.TaskListUpdateDTO;
import com.task.DTO.TaskResponeDTO;
import com.task.model.Task;
import com.task.model.TaskList;
import com.task.model.User;
import com.task.service.BoardService;
import com.task.service.TaskListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RestController
@RequestMapping("/api/tasklist")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "TaskList Controller", description = "Quản lý list task")
public class TaskListRest {
	private final TaskListService taskListService;
	private final BoardService boardService;

	@GetMapping
	@Operation(summary = "Lấy danh sách listtask")
	public ResponseEntity<List<TaskListResponeDTO>> getAllTaskList() {
		List<TaskList> taskLists = taskListService.findAll();
		List<TaskListResponeDTO> result = taskLists.stream().map(t -> mapToResponseDTO(t)).toList();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lấy TaskList chi tiết")
	public ResponseEntity<TaskListResponeDTO> getDetailTaskList(@PathVariable UUID id) {

		return taskListService.findById(id).map(t -> ResponseEntity.ok(mapToResponseDTO(t)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping()
	@Operation(summary = "Tạo task list mới")
	public ResponseEntity<TaskListResponeDTO> createTaskList(@Valid @RequestBody TaskListCreateDTO dto) {
		TaskList taskList = mapToEntity(dto);
		TaskList save = taskListService.save(taskList);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(save));
	}

	@PutMapping("/{id}")
	@Operation(summary = "chỉnh sửa task list")
	public ResponseEntity<TaskListResponeDTO> editTaskList(@PathVariable UUID id,
			@Valid @RequestBody TaskListUpdateDTO dto) {
		Optional<TaskList> otpTaskList = taskListService.findById(id);
		if (otpTaskList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		TaskList taskList = otpTaskList.get();
		taskList.setName(dto.getName());
		taskList.setPosition(dto.getPosition());
		taskList.setBoard(boardService.findById(dto.getBoardId()).orElseThrow());
		TaskList updated = taskListService.save(taskList);
		return ResponseEntity.ok(mapToResponseDTO(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTaskList(@PathVariable UUID id) {
		taskListService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private TaskList mapToEntity(TaskListCreateDTO dto) {
		// Tìm user từ UUID
		TaskList taskList = new TaskList();
		taskList.setName(dto.getName());
		taskList.setPosition(dto.getPosition());
		taskList.setBoard(
				boardService.findById(dto.getBoardId()).orElseThrow(() -> new RuntimeException("boardID not found")));
		return taskList;

	}

	private TaskListResponeDTO mapToResponseDTO(TaskList taskList) {
		TaskListResponeDTO dto = new TaskListResponeDTO();
		dto.setId(taskList.getId());
		dto.setName(taskList.getName());
		dto.setPosition(taskList.getPosition());
		dto.setBoardId(taskList.getBoard().getId());
		return dto;
	}
}
