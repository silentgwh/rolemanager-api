package by.mosquitto.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PrivilegeDto {
    @NotBlank(message = "Поле 'name' обязательно")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Поле 'name' должно содержать только латинские буквы")
    private String name;

    @NotNull(message = "Поле 'type' обязательно")
    @Min(0)
    @Max(1)
    private Short type;

    @NotBlank(message = "Поле 'name2' обязательно")
    private String name2;

    @NotBlank(message = "Поле 'comment' обязательно")
    private String comment;
}
