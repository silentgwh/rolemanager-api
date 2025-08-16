package by.mosquitto.mapper;

import by.mosquitto.dto.PrivilegeDto;
import by.mosquitto.dto.response.PrivilegeResponseDto;
import by.mosquitto.entity.Privilege;
import by.mosquitto.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PrivilegeMapper {
    public Privilege toEntity(PrivilegeDto dto, User user) {
        Privilege entity = new Privilege();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setName2(dto.getName2());
        entity.setComment(dto.getComment());
        entity.setDateCorr(LocalDateTime.now());
        entity.setUserCorr(user);
        return entity;
    }

    public PrivilegeResponseDto toDto(Privilege entity) {
        PrivilegeResponseDto dto = new PrivilegeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setName2(entity.getName2());
        dto.setComment(entity.getComment());
        dto.setDateCorr(entity.getDateCorr());
        dto.setUserCorrId(Long.valueOf(entity.getUserCorr() != null ? entity.getUserCorr().getId() : null));
        return dto;
    }
}
