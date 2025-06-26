package com.task.RestController;


import com.task.DTO.LoginRequest;
import com.task.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRest {

    private final AuthService authService;

    public AuthRest(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.authenticateAndGenerateToken(request);
        return ResponseEntity.ok().body(token); // Trả JWT token cho FE
    }
}
