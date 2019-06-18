package by.itechart.server.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String loginOrEmail;

    @NotBlank
    private String password;
}
