package by.mosquitto.mapper;

import by.mosquitto.dto.ProfileDto;
import by.mosquitto.dto.response.ProfileResponseDto;
import by.mosquitto.entity.Profile;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;
import by.mosquitto.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Profile toEntity(ProfileDto dto, User user) {
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setComment(dto.getComment());
        profile.setDateCorr(LocalDateTime.now());
        profile.setUserCorr(user);

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
        profile.setRoles(roles);

        return profile;
    }

    public ProfileResponseDto toDto(Profile profile) {
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setId(profile.getId());
        dto.setName(profile.getName());
        dto.setComment(profile.getComment());
        dto.setDateCorr(profile.getDateCorr());
        dto.setUserCorrId(profile.getUserCorr() != null ? profile.getUserCorr().getId() : null);
        dto.setRoles(profile.getRoles().stream()
                .map(roleMapper::toDto)
                .toList());
        return dto;
    }
}
