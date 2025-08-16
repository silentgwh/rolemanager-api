package by.mosquitto.seed;

import by.mosquitto.entity.*;
import by.mosquitto.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    @Override
    public void run(String... args) {
        final String username = "admin";
        final String profileName = "ADMIN_PROFILE";
        final String roleName = "ADMIN";

        if (userRepository.existsByUsername(username)) {
            log.info("Admin user already exists");
            return;
        }

        // Create Role
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role r = Role.builder()
                            .name(roleName)
                            .type((short) 1) // публичная роль
                            .comment("Administrator role")
                            .dateCorr(LocalDateTime.now())
                            .build();
                    return roleRepository.save(r);
                });

        // Create Profile
        Profile profile = profileRepository.findByName(profileName)
                .orElseGet(() -> {
                    Profile p = Profile.builder()
                            .name(profileName)
                            .comment("Administrator profile")
                            .dateCorr(LocalDateTime.now())
                            .build();
                    return profileRepository.save(p);
                });

        // Create User
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode("admin123"))
                .profile(profile)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        log.info("Admin user created with ID {}", savedUser.getId());

        // Update user_corr
        profile.setUserCorr(savedUser);
        role.setUserCorr(savedUser);
        profileRepository.save(profile);
        roleRepository.save(role);

        // Link Role to Profile
        profile.getRoles().add(role);
        profileRepository.save(profile);
        log.info("Linked ADMIN_ROLE to ADMIN_PROFILE");

        // Link only public privileges to Role
        List<Privilege> publicPrivileges = privilegeRepository.findByType((short) 1);
        role.getPrivileges().addAll(publicPrivileges);
        roleRepository.save(role);
        log.info("Linked {} public privileges to ADMIN_ROLE", publicPrivileges.size());
    }
}

