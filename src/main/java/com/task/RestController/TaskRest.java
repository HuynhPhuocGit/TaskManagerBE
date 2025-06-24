package com.task.RestController;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.Task;
import com.task.service.TaskService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Task controller", description = "Quản lý các task")
public class TaskRest {
	 private final TaskService taskService;

	@GetMapping
	public List<Task> getListTask(){
		return  taskService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetailTask(@PathVariable UUID id){
		return taskService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody Task task){
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
	}
	
//	@PutMapping("/{id}")
//	public ResponseEntity<Task> editTask(@PathVariable UUID id,@RequestBody Task task){
//		
//	}

}
