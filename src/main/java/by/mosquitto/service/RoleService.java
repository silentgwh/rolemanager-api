package by.mosquitto.service;

import by.mosquitto.dto.RoleDto;
import by.mosquitto.dto.response.RoleResponseDto;
import by.mosquitto.entity.Privilege;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;
import by.mosquitto.mapper.RoleMapper;
import by.mosquitto.repository.RolePrivilegeRepository;
import by.mosquitto.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    public RoleResponseDto create(RoleDto dto, User user) {
        if (roleRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Роль с таким именем уже существует");
        }

        Role role = roleMapper.toEntity(dto, user);
        Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    public RoleResponseDto update(Long id, RoleDto dto, User user) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Роль не найдена"));

        if (!role.getName().equalsIgnoreCase(dto.getName()) &&
                roleRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Имя роли уже занято");
        }

        role.setName(dto.getName());
        role.setComment(dto.getComment());
        role.setType(dto.getType());
        role.setDateCorr(LocalDateTime.now());
        role.setUserCorr(user);

        Set<Privilege> privileges = new HashSet<>(dto.getPrivilegeIds().stream()
                .map(Privilege::new)
                .toList());
        role.setPrivileges(privileges);

        Role updated = roleRepository.save(role);
        return roleMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (rolePrivilegeRepository.existsByRoleId(id)) {
            throw new IllegalStateException("Невозможно удалить: роль связана с профилями");
        }

        roleRepository.deleteById(id);
    }

    public List<RoleResponseDto> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    public RoleResponseDto getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Роль не найдена"));
        return roleMapper.toDto(role);
    }
}
