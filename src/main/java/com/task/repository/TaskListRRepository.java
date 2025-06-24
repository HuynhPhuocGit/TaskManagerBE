package com.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.task.model.TaskList;

public interface TaskListRRepository extends JpaRepository<TaskList,UUID>{

}
