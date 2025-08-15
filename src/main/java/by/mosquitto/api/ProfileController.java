package by.mosquitto.api;

import by.mosquitto.auth.CustomUserDetails;
import by.mosquitto.dto.ProfileDto;
import by.mosquitto.dto.response.ProfileResponseDto;
import by.mosquitto.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Validated
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<ProfileResponseDto> createProfile(
            @Valid @RequestBody ProfileDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ProfileResponseDto created = profileService.create(dto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ProfileResponseDto updated = profileService.update(id, dto, userDetails.getUser());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<List<ProfileResponseDto>> getAllProfiles() {
        List<ProfileResponseDto> profiles = profileService.getAll();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<ProfileResponseDto> getProfileById(@PathVariable Long id) {
        ProfileResponseDto profile = profileService.getById(id);
        return ResponseEntity.ok(profile);
    }
}
