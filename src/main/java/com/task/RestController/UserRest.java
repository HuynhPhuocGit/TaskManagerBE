package com.task.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.UserCreateDTO;
import com.task.DTO.UserResponseDTO;
import com.task.DTO.UserUpdateDTO;
import com.task.model.User;
import com.task.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Quản lý người dùng")
public class UserRest {
    private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@Operation(summary = "Lấy danh sách người dùng")
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUser() {
		List<User> users = userService.findAll();
		List<UserResponseDTO> result = users.stream().map(this::mapToResponseDTO).toList();
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "Lấy user theo id")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
		return userService.findById(id)
				.map(user -> ResponseEntity.ok(mapToResponseDTO(user)))
				.orElse(ResponseEntity.notFound().build());
	}
	

	@Operation(summary = "Xóa người dùng")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Tạo người dùng")
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO dto) {
		User user = mapToEntity(dto);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		User saved = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(saved));
	}

	@Operation(summary = "Cập nhật người dùng")
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> editUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO dto) {
		Optional<User> optionalUser = userService.findById(id);
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		User user = optionalUser.get();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());
		User updated = userService.save(user);
		return ResponseEntity.ok(mapToResponseDTO(updated));
	}


	// ---------- Mapping methods ----------
	private User mapToEntity(UserCreateDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(dto.getRole());
		return user;
	}

	private UserResponseDTO mapToResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;
	}
}

