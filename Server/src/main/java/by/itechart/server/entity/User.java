package by.itechart.server.entity;

import by.itechart.server.dto.UserDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "user")
public class User implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 45, message = "Patronymic must be between 2 and 45 characters")
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 2, max = 45, message = "Surname must be between 2 and 45 characters")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Passport number cannot be null")
    @Size(min = 2, max = 45, message = "Passport number must be between 2 and 45 characters")
    @Column(name = "passport_number")
    private String passportNumber;

    @NotNull(message = "Passport issued cannot be null")
    @Size(min = 2, max = 45, message = "Passport number must be between 2 and 45 characters")
    @Column(name = "passport_issued")
    private String passportIssued;

    @Past(message = "Wrong date of birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Email
    @Column(name = "email")
    private String email;

    @Size(min = 2, max = 45, message = "Login number must be between 2 and 45 characters")
    @Column(name = "login")
    private String login;

    @Size(min = 2, message = "Password number must be between 2 and 45 characters")
    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_id")
    private List<Role> roles;

    /**
     * One user can have only one address.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

    /**
     * One user can belong to only one clientCompany.
     * The same clientCompany may have many users.
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public User() {
        super();
        this.isEnabled = false;
    }

    @Override
    public UserDto transformToDto() {
        UserDto.Builder userDtoBuilder = UserDto.builder()
                .withId(this.id)
                .withEnabled(this.isEnabled)
                .withDateOfBirth(this.dateOfBirth)
                .withLogin(this.login)
                .withPassword(this.password)
                .withName(this.name)
                .withPassportNumber(this.passportNumber)
                .withPassportIssued(this.passportIssued)
                .withPatronymic(this.patronymic)
                .withSurname(this.surname)
                .withRoles(this.roles)
                .withEmail(this.email)
                .withAddressDto(this.address.transformToDto());
        if (this.clientCompany != null) {
            userDtoBuilder.withClientCompany(this.clientCompany.transformToDto());
        }
        return userDtoBuilder.build();
    }

    public Integer getId() {
        return id;
    }

    public User setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(final String name) {
        this.name = name;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public User setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(final String surname) {
        this.surname = surname;
        return this;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public User setPassportNumber(final String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public String getPassportIssued() {
        return passportIssued;
    }

    public User setPassportIssued(final String passportIssued) {
        this.passportIssued = passportIssued;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public User setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(final String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(final List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(final Address address) {
        this.address = address;
        return this;
    }

    public ClientCompany getClientCompany() {
        return clientCompany;
    }

    public User setClientCompany(final ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
        return this;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public User setEnabled(final Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public enum Role {
        SYSADMIN,
        ADMIN,
        MANAGER,
        DISPATCHER,
        DRIVER,
        OWNER
    }

}