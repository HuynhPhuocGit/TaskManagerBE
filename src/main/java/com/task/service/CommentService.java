package com.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.model.Comment;
import com.task.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public <S extends Comment> S save(S entity) {
		return commentRepository.save(entity);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	

	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	public Optional<Comment> findById(UUID id) {
		return commentRepository.findById(id);
	}

	public void deleteById(UUID id) {
		commentRepository.deleteById(id);
	}
	
	
}
