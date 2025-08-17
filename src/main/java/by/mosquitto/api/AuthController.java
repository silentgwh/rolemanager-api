package by.mosquitto.api;

import by.mosquitto.dto.UserInfoDto;
import by.mosquitto.entity.User;
import by.mosquitto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/login")
    public ResponseEntity<UserInfoDto> login(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRole() != null ? user.getRole().getName() : "NO_ROLE";

        log.info("User '{}' logged in with role '{}'", user.getUsername(), role);

        return ResponseEntity.ok(
                UserInfoDto.builder()
                        .username(user.getUsername())
                        .roleName(role)
                        .build()
        );
    }
}
