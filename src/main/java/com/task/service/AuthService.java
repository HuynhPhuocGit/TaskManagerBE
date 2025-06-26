package com.task.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.LoginRequest;
import com.task.config.JwtUtil;
import com.task.model.User;
import com.task.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	 private final UserRepository userRepository;
	    private final JwtUtil jwtUtil;
	    private final PasswordEncoder passwordEncoder;

//	    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
//	        this.userRepository = userRepository;
//	        this.jwtUtil = jwtUtil;
//	        this.passwordEncoder = passwordEncoder;
//	    }

	    public String authenticateAndGenerateToken(LoginRequest request) {
	        User user = userRepository.findByUsername(request.getUsername())
	                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Sai mật khẩu");
	        }

	        return jwtUtil.generateToken(user);
	    }
}
