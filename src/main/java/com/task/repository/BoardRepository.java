package com.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Boards;

public interface BoardRepository  extends JpaRepository<Boards, UUID>{

}
