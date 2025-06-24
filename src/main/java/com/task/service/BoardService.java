package com.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.model.Boards;
import com.task.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
private final BoardRepository boardRepository;

public <S extends Boards> S save(S entity) {
	return boardRepository.save(entity);
}

public Page<Boards> findAll(Pageable pageable) {
	return boardRepository.findAll(pageable);
}

public List<Boards> findAll() {
	return boardRepository.findAll();
}

public Optional<Boards> findById(UUID id) {
	return boardRepository.findById(id);
}

public void deleteById(UUID id) {
	boardRepository.deleteById(id);
}




}
