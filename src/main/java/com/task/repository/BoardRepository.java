package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Boards;

public interface BoardRepository  extends JpaRepository<Boards, Long>{

}
