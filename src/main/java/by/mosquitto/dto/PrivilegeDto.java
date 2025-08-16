package by.mosquitto.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PrivilegeDto {

    @NotBlank(message = "Поле 'name' не должно быть пустым")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Поле 'name' должно содержать только латинские буквы, цифры и подчёркивания")
    private String name;

    @NotNull(message = "Поле 'type' обязательно")
    @Min(value = 0, message = "Тип должен быть 0 или 1")
    @Max(value = 1, message = "Тип должен быть 0 или 1")
    private Short type;

    @NotBlank(message = "Поле 'name2' не должно быть пустым")
    private String name2;

    @NotBlank(message = "Поле 'comment' не должно быть пустым")
    private String comment;
}
