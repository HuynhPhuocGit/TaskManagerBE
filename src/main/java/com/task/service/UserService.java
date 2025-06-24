package com.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.model.User;
import com.task.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}



	public void delete(User entity) {
		userRepository.delete(entity);
	}

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	public void deleteById(UUID id) {
		userRepository.deleteById(id);
	}

	
	
}
