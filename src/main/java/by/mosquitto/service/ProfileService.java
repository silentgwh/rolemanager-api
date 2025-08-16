package by.mosquitto.service;

import by.mosquitto.dto.ProfileDto;
import by.mosquitto.dto.response.ProfileResponseDto;
import by.mosquitto.entity.Profile;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;
import by.mosquitto.mapper.ProfileMapper;
import by.mosquitto.repository.ProfileRepository;
import by.mosquitto.repository.RoleRepository;
import by.mosquitto.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final ProfileMapper profileMapper;

    public ProfileResponseDto create(ProfileDto dto, User user) {
        if (profileRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Профиль с таким именем уже существует");
        }

        Profile profile = profileMapper.toEntity(dto, user);
        Profile saved = profileRepository.save(profile);
        return profileMapper.toDto(saved);
    }

    public ProfileResponseDto update(Long id, ProfileDto dto, User user) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Профиль не найден"));

        if (!profile.getName().equalsIgnoreCase(dto.getName()) &&
                profileRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Имя профиля уже занято");
        }

        profile.setName(dto.getName());
        profile.setComment(dto.getComment());
        profile.setDateCorr(LocalDateTime.now());
        profile.setUserCorr(user);

        if (dto.getRoleIds() != null) {
            List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
            profile.setRoles(new HashSet<>(roles));
        }

        Profile updated = profileRepository.save(profile);
        return profileMapper.toDto(updated);
    }


    public void delete(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new EntityNotFoundException("Профиль не найден");
        }

        // TODO: Проверка на связанные пользователи, если появится таблица USER_PROFILE

        profileRepository.deleteById(id);
    }

    public List<ProfileResponseDto> getAll() {
        return profileRepository.findAll().stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public ProfileResponseDto getById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Профиль не найден"));
        return profileMapper.toDto(profile);
    }
}
