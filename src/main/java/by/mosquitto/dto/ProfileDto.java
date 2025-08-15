package by.mosquitto.dto;

import lombok.Data;

import java.util.Set;
import jakarta.validation.constraints.*;

@Data
public class ProfileDto {

    @NotBlank(message = "Поле 'name' обязательно")
    private String name;

    @NotBlank(message = "Поле 'comment' обязательно")
    private String comment;

    @NotEmpty(message = "Необходимо выбрать хотя бы одну роль")
    private Set<Long> roleIds;
}
