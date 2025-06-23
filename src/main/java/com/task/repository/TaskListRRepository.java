package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.task.model.TaskList;

public interface TaskListRRepository extends JpaRepository<TaskList,Long >{

}
