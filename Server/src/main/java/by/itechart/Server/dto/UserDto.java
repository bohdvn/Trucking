package by.itechart.Server.dto;

import by.itechart.Server.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private int id;

    private String name;

    private Boolean isEnabled;

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

    private List<InvoiceDto> checkedByManagerInvoices;

    private List<InvoiceDto> issuedByDispatcherFromInvoices;

    private List<InvoiceDto> issuedByDispatcherToInvoices;

    private List<RequestDto> requests;

    private ClientCompanyDto clientCompany;

    private UserDto() {
    }

    public static Builder builder() {
        return new UserDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withCheckedByManagerInvoices(final List<InvoiceDto> checkedByManagerInvoices) {
            UserDto.this.checkedByManagerInvoices = checkedByManagerInvoices;
            return this;
        }

        public Builder withIssuedByDispatcherFromInvoices(final List<InvoiceDto> issuedByDispatcherFromInvoices) {
            UserDto.this.issuedByDispatcherFromInvoices = issuedByDispatcherFromInvoices;
            return this;
        }

        public Builder withIssuedByDispatcherToInvoices(final List<InvoiceDto> issuedByDispatcherToInvoices) {
            UserDto.this.issuedByDispatcherToInvoices = issuedByDispatcherToInvoices;
            return this;
        }

        public Builder withClientCompany(final ClientCompanyDto clientCompany) {
            UserDto.this.clientCompany = clientCompany;
            return this;
        }


        public Builder withRequests(final List<RequestDto> requests) {
            UserDto.this.requests = requests;
            return this;
        }

        public Builder withEnabled(final boolean isEnable) {
            UserDto.this.isEnabled = isEnabled;
            return this;
        }

        public Builder withId(final int id) {
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