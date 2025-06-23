package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
