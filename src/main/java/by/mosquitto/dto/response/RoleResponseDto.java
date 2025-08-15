package by.mosquitto.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleResponseDto {
    private Long id;
    private String name;
    private Short type;
    private String comment;
    private LocalDateTime dateCorr;
    private Long userCorrId;
    private List<PrivilegeResponseDto> privileges;
}
