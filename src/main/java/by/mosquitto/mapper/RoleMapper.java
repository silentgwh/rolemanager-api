package by.mosquitto.mapper;

import by.mosquitto.dto.RoleDto;
import by.mosquitto.dto.response.RoleResponseDto;
import by.mosquitto.entity.Privilege;
import by.mosquitto.entity.Role;
import by.mosquitto.entity.User;
import by.mosquitto.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    public Role toEntity(RoleDto dto, User user) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setType(dto.getType());
        role.setComment(dto.getComment());
        role.setDateCorr(LocalDateTime.now());
        role.setUserCorr(user);

        Set<Privilege> privileges = new HashSet<>(privilegeRepository.findAllById(dto.getPrivilegeIds()));
        role.setPrivileges(privileges);

        return role;
    }

    public RoleResponseDto toDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setType(role.getType());
        dto.setComment(role.getComment());
        dto.setDateCorr(role.getDateCorr());
        dto.setUserCorrId(role.getUserCorr() != null ? role.getUserCorr().getId() : null);
        dto.setPrivileges(role.getPrivileges().stream()
                .map(privilegeMapper::toDto)
                .toList());
        return dto;
    }
}
