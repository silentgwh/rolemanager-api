package by.mosquitto.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfileResponseDto {
    private Integer id;
    private String name;
    private String comment;
    private LocalDateTime dateCorr;
    private Long userCorrId;
    private List<RoleResponseDto> roles;
}
