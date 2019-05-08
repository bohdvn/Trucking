package by.itechart.Server.dto;

import java.time.LocalDate;


public class UserDto {

    private String name;

    private String patronymic;

    private String surname;

    private String passportNumber;

    private LocalDate dateOfBirth;

    private String login;

    private String password;

    private UserDto(){}

    public static Builder builder() {
        return new UserDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withName(final String name) {
            UserDto.this.name = name;
            return this;
        }

        public Builder withSurname(final String surname) {
            UserDto.this.surname = surname;
            return this;
        }

        public Builder withPatronymic(final String patronymic) {
            UserDto.this.patronymic = patronymic;
            return this;
        }

        public Builder withPassportNumber(final String passportNumber) {
            UserDto.this.passportNumber = passportNumber;
            return this;
        }

        public Builder withDateOfBirth(final LocalDate dateOfBirth) {
            UserDto.this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withLogin(final String login) {
            UserDto.this.login = login;
            return this;
        }

        public Builder withPassword(final String password) {
            UserDto.this.password = password;
            return this;
        }

        public UserDto build() {
            return UserDto.this;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(final String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}