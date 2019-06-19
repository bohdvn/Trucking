package by.itechart.server.dto;

import by.itechart.server.entity.User;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto implements ToEntityTransformer {

    private Integer id;

    private String name;

    private boolean isEnabled;

    private String patronymic;

    private String surname;

    private String passportNumber;

    private String passportIssued;

    private LocalDate dateOfBirth;

    private String email;

    private List<User.Role> roles;

    private String login;

    private String password;

    private AddressDto address;

    private ClientCompanyDto clientCompany;

    private UserDto() {
        this.isEnabled = false;
    }

    public static Builder builder() {
        return new UserDto().new Builder();
    }

    @Override
    public User transformToEntity() {
        final User user = new User();
        user.setAddress(this.address.transformToEntity());
        user.setClientCompany(this.clientCompany != null ? this.clientCompany.transformToEntity() : null);
        user.setDateOfBirth(this.dateOfBirth);
        user.setEmail(this.email);
        user.setEnabled(this.isEnabled);
        user.setId(this.id);
        user.setLogin(this.login);
        user.setName(this.name);
        user.setPassportIssued(this.passportIssued);
        user.setPassportNumber(this.passportNumber);
        user.setPassword(this.password);
        user.setPatronymic(this.patronymic);
        user.setRoles(this.roles);
        user.setSurname(this.surname);
        return user;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withClientCompany(final ClientCompanyDto clientCompany) {
            UserDto.this.clientCompany = clientCompany;
            return this;
        }


        public Builder withEnabled(final boolean isEnabled) {
            UserDto.this.isEnabled = isEnabled;
            return this;
        }

        public Builder withId(final Integer id) {
            UserDto.this.id = id;
            return this;
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

        public Builder withPassportIssued(final String passportIssued) {
            UserDto.this.passportIssued = passportIssued;
            return this;
        }

        public Builder withDateOfBirth(final LocalDate dateOfBirth) {
            UserDto.this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withRoles(final List<User.Role> roles) {
            UserDto.this.roles = roles;
            return this;
        }

        public Builder withLogin(final String login) {
            UserDto.this.login = login;
            return this;
        }

        public Builder withEmail(final String email) {
            UserDto.this.email = email;
            return this;
        }

        public Builder withPassword(final String password) {
            UserDto.this.password = password;
            return this;
        }

        public Builder withAddressDto(final AddressDto addressDto) {
            UserDto.this.address = addressDto;
            return this;
        }

        public UserDto build() {
            return UserDto.this;
        }
    }
}