package com.task.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.task.model.User;
import com.task.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Quản lý người dùng")
public class UserRest {
	private final UserService userService;

	@Operation(summary = "Lấy danh sách người dùng")
	@GetMapping
	public List<User> getAllUser() {
		return userService.findAll();
	}

	@Operation(summary = "Lấy user theo id")
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.findById(id).map(user -> ResponseEntity.ok(user)) // status 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // status 404
	}

	@Operation(summary = "Tạo người dùng")
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@Operation(summary = "Xóa người dùng")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build(); // HTTP 204 No Content
	}

	@Operation(summary = "Cập nhật người dùng")
	@PutMapping("/{id}")
	public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
		Optional<User> optionalUser = userService.findById(id);
		if (optionalUser.isEmpty()) {
			return ResponseEntity.notFound().build(); // 404 nếu không tồn tại
		}
		User existingUser = optionalUser.get();
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setRole(user.getRole());

		User savedUser = userService.save(existingUser);

		return ResponseEntity.ok(savedUser); // Trả về user đã được cập nhật
	}

}
