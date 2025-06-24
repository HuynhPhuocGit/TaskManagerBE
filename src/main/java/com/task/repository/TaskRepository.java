package com.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
