package com.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.model.Task;
import com.task.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
private final TaskRepository taskRepository;

public <S extends Task> S save(S entity) {
	return taskRepository.save(entity);
}

public List<Task> findAll() {
	return taskRepository.findAll();
}



public Page<Task> findAll(Pageable pageable) {
	return taskRepository.findAll(pageable);
}

public Optional<Task> findById(UUID id) {
	return taskRepository.findById(id);
}

public void deleteById(UUID id) {
	taskRepository.deleteById(id);
}

	
}
