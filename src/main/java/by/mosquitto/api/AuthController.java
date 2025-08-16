package by.mosquitto.api;

import by.mosquitto.dto.request.RegisterRequest;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;
import by.mosquitto.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // можно задать роль по умолчанию:
        user.setRole(defaultRole());

        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    private Role defaultRole() {
        Role role = new Role();
        role.setId(2L); // например, USER
        return role;
    }
}
