package by.mosquitto.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDto {
    @NotBlank(message = "Поле 'name' обязательно")
    private String name;

    @NotNull(message = "Поле 'type' обязательно")
    @Min(0)
    @Max(1)
    private Short type;

    @NotBlank(message = "Поле 'comment' обязательно")
    private String comment;

    @NotEmpty(message = "Необходимо выбрать хотя бы одну привилегию")
    private Set<Long> privilegeIds;
}
