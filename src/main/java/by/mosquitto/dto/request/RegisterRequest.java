package by.mosquitto.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
