package by.itechart.server.dto;

import by.itechart.server.annotations.CriteriaAnnotation;
import by.itechart.server.entity.User;
import by.itechart.server.interfaces.FieldsInterface;
import by.itechart.server.transformers.ToEntityTransformer;

import java.time.LocalDate;


public class UserDto implements ToEntityTransformer, FieldsInterface {

    private int id;

    @CriteriaAnnotation
    private String name;

    private Boolean isEnabled;

    @CriteriaAnnotation
    private String patronymic;

    @CriteriaAnnotation
    private String surname;

    private String passportNumber;

    private String passportIssued;

    private LocalDate dateOfBirth;

    private String email;

    private User.Role role;

    private String login;

    private String password;

    private AddressDto address;

//    private List<InvoiceDto> checkedByManagerInvoices;
//
//    private List<InvoiceDto> issuedByDispatcherFromInvoices;
//
//    private List<InvoiceDto> issuedByDispatcherToInvoices;
//
//    private List<RequestDto> requests;

    private ClientCompanyDto clientCompany;

    private UserDto() {
    }

    public static Builder builder() {
        return new UserDto().new Builder();
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(final Boolean enabled) {
        isEnabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User.Role getRole() {
        return role;
    }

    public void setRole(final User.Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

//    public List<InvoiceDto> getCheckedByManagerInvoices() {
//        return checkedByManagerInvoices;
//    }
//
//    public void setCheckedByManagerInvoices(final List<InvoiceDto> checkedByManagerInvoices) {
//        this.checkedByManagerInvoices = checkedByManagerInvoices;
//    }
//
//    public List<InvoiceDto> getIssuedByDispatcherFromInvoices() {
//        return issuedByDispatcherFromInvoices;
//    }
//
//    public void setIssuedByDispatcherFromInvoices(final List<InvoiceDto> issuedByDispatcherFromInvoices) {
//        this.issuedByDispatcherFromInvoices = issuedByDispatcherFromInvoices;
//    }
//
//    public List<InvoiceDto> getIssuedByDispatcherToInvoices() {
//        return issuedByDispatcherToInvoices;
//    }
//
//    public void setIssuedByDispatcherToInvoices(final List<InvoiceDto> issuedByDispatcherToInvoices) {
//        this.issuedByDispatcherToInvoices = issuedByDispatcherToInvoices;
//    }
//
//    public List<RequestDto> getRequests() {
//        return requests;
//    }
//
//    public void setRequests(final List<RequestDto> requests) {
//        this.requests = requests;
//    }

    public ClientCompanyDto getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(final ClientCompanyDto clientCompany) {
        this.clientCompany = clientCompany;
    }

    public String getPassportIssued() {
        return passportIssued;
    }

    public void setPassportIssued(final String passportIssued) {
        this.passportIssued = passportIssued;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(final AddressDto address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public User transformToEntity() {
        final User user = new User();
        user.setAddress(this.address.transformToEntity());
        user.setClientCompany(this.clientCompany.transformToEntity());
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
        user.setRole(this.role);
        user.setSurname(this.surname);
        return user;
    }

    public class Builder {
        private Builder() {
        }

//        public Builder withCheckedByManagerInvoices(final List<InvoiceDto> checkedByManagerInvoices) {
//            UserDto.this.checkedByManagerInvoices = checkedByManagerInvoices;
//            return this;
//        }
//
//        public Builder withIssuedByDispatcherFromInvoices(final List<InvoiceDto> issuedByDispatcherFromInvoices) {
//            UserDto.this.issuedByDispatcherFromInvoices = issuedByDispatcherFromInvoices;
//            return this;
//        }
//
//        public Builder withIssuedByDispatcherToInvoices(final List<InvoiceDto> issuedByDispatcherToInvoices) {
//            UserDto.this.issuedByDispatcherToInvoices = issuedByDispatcherToInvoices;
//            return this;
//        }

        public Builder withClientCompany(final ClientCompanyDto clientCompany) {
            UserDto.this.clientCompany = clientCompany;
            return this;
        }


//        public Builder withRequests(final List<RequestDto> requests) {
//            UserDto.this.requests = requests;
//            return this;
//        }

        public Builder withEnabled(final boolean isEnabled) {
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

        public Builder withRole(final User.Role role) {
            UserDto.this.role = role;
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