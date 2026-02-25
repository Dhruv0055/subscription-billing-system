package com.subscription.billing.controller;

import com.subscription.billing.dto.LoginResponse;
import com.subscription.billing.entity.User;
import com.subscription.billing.repository.UserRepository;
import com.subscription.billing.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");   // ✅ Default role

        User saved = userRepository.save(user);

        return ResponseEntity.ok(saved);
    }

    //(BCrypt MATCH)
    @PostMapping("/login")
    public LoginResponse login(@RequestBody User loginData) {

        User user = userRepository.findByEmail(loginData.getEmail()).orElse(null);

        if (user == null)
            return new LoginResponse(false, "User not found", null);

        if (!passwordEncoder.matches(loginData.getPassword(), user.getPassword()))
            return new LoginResponse(false, "Wrong password", null);

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(true, "Login success", token);
    }
}
