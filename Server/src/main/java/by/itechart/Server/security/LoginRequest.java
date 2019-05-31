package by.itechart.Server.security;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String loginOrEmail;

    @NotBlank
    private String password;

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
