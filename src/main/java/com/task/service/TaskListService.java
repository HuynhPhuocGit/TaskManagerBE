package com.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.model.TaskList;
import com.task.repository.TaskListRRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskListService {
private final TaskListRRepository taskListRRepository;

public <S extends TaskList> S save(S entity) {
	return taskListRRepository.save(entity);
}

public List<TaskList> findAll() {
	return taskListRRepository.findAll();
}



public Page<TaskList> findAll(Pageable pageable) {
	return taskListRRepository.findAll(pageable);
}

public Optional<TaskList> findById(UUID id) {
	return taskListRRepository.findById(id);
}

public void deleteById(UUID id) {
	taskListRRepository.deleteById(id);
}

}
