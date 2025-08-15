package by.mosquitto.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivilegeResponseDto {
    private Long id;
    private String name;
    private Short type;
    private String name2;
    private String comment;
    private LocalDateTime dateCorr;
    private Long userCorrId;
}
