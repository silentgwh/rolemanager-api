package by.mosquitto.service;

import by.mosquitto.dto.PrivilegeDto;
import by.mosquitto.dto.response.PrivilegeResponseDto;
import by.mosquitto.entity.Privilege;
import by.mosquitto.entity.User;
import by.mosquitto.mapper.PrivilegeMapper;
import by.mosquitto.repository.PrivilegeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper mapper;

    public List<PrivilegeResponseDto> getAll() {
        return privilegeRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public PrivilegeResponseDto getById(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Привилегия не найдена"));
        return mapper.toDto(privilege);
    }

    public PrivilegeResponseDto create(PrivilegeDto dto, User user) {
        if (privilegeRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Привилегия с таким именем уже существует");
        }

        Privilege entity = mapper.toEntity(dto, user);
        Privilege saved = privilegeRepository.save(entity);
        return mapper.toDto(saved);
    }

    public PrivilegeResponseDto update(Long id, PrivilegeDto dto, User user) {
        Privilege existing = privilegeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Привилегия не найдена"));

        if (!existing.getName().equals(dto.getName()) &&
                privilegeRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Привилегия с таким именем уже существует");
        }

        existing.setName(dto.getName());
        existing.setType(dto.getType());
        existing.setName2(dto.getName2());
        existing.setComment(dto.getComment());
        existing.setDateCorr(LocalDateTime.now());
        existing.setUserCorr(user);

        Privilege updated = privilegeRepository.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Привилегия не найдена"));

        if (!privilege.getRoles().isEmpty()) {
            throw new IllegalStateException("Невозможно удалить: привилегия связана с ролями");
        }

        privilegeRepository.delete(privilege);
    }
}
