package by.mosquitto.seed;
import by.mosquitto.entity.Profile;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;

import by.mosquitto.repository.ProfileRepository;
import by.mosquitto.repository.RoleRepository;
import by.mosquitto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

;

@RequiredArgsConstructor
@Component
public class SysAdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(SysAdminSeeder.class);

    @Override
    public void run(String... args) {
        final String username = "sysadmin";
        final String profileName = "SYSADMIN_PROFILE";
        final String roleName = "SYSADMIN";

        if (userRepository.existsByUsername(username)) {
            log.info("Sysadmin user already exists");
            return;
        }

        // Ensure Role exists
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    newRole.setType((short) 0); // system role
                    newRole.setComment("System administrator role");
                    Role savedRole = roleRepository.save(newRole);
                    log.info("Created role: {}", roleName);
                    return savedRole;
                });

        // Ensure Profile exists
        Profile profile = profileRepository.findByName(profileName)
                .orElseGet(() -> {
                    Profile newProfile = new Profile();
                    newProfile.setName(profileName);
                    newProfile.setComment("System administrator profile");
                    Profile savedProfile = profileRepository.save(newProfile);
                    log.info("Created profile: {}", profileName);
                    return savedProfile;
                });

        // Create sysadmin user
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("sys123"));
        user.setProfile(profile);
        user.setRole(role);

        User savedUser = userRepository.save(user);
        log.info("Sysadmin user seeded successfully with ID {}", savedUser.getId());

        // Optionally: update user_corr in profile and role
        profile.setUserCorr(savedUser);
        role.setUserCorr(savedUser);
        profileRepository.save(profile);
        roleRepository.save(role);
        log.info("Updated user_corr for profile and role");
    }
}
