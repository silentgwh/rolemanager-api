package by.mosquitto.api;

import by.mosquitto.auth.CustomUserDetails;
import by.mosquitto.dto.PrivilegeDto;
import by.mosquitto.dto.response.PrivilegeResponseDto;
import by.mosquitto.service.PrivilegeService;
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
@RequestMapping("/api/privileges")
@RequiredArgsConstructor
@Validated
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @PostMapping
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<PrivilegeResponseDto> createPrivilege(
            @Valid @RequestBody PrivilegeDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        PrivilegeResponseDto created = privilegeService.create(dto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<PrivilegeResponseDto> updatePrivilege(
            @PathVariable Long id,
            @Valid @RequestBody PrivilegeDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        PrivilegeResponseDto updated = privilegeService.update(id, dto, userDetails.getUser());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<List<PrivilegeResponseDto>> getAllPrivileges() {
        List<PrivilegeResponseDto> privileges = privilegeService.getAll();
        return ResponseEntity.ok(privileges);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SYSADMIN')")
    public ResponseEntity<PrivilegeResponseDto> getPrivilegeById(@PathVariable Long id) {
        PrivilegeResponseDto privilege = privilegeService.getById(id);
        return ResponseEntity.ok(privilege);
    }
}

