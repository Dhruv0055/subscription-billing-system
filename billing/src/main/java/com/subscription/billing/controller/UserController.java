package com.subscription.billing.controller;

import com.subscription.billing.entity.User;
import com.subscription.billing.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.subscription.billing.dto.LoginRequest;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);

    }


    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser) {
        return userRepository.findById(id).map(u -> {
            u.setName(newUser.getName());
            u.setEmail(newUser.getEmail());
            u.setPassword(newUser.getPassword());
            return userRepository.save(u);
        }).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return "Invalid password";
        }

        return "Login successful. User ID: " + user.getId();
    }
}
