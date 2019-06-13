package by.itechart.Server.entity;

import by.itechart.Server.dto.UserDto;
import lombok.Data;
import by.itechart.Server.transformers.ToDtoTransformer;

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
        UserDto.Builder userDtoBuilder=UserDto.builder()
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

    public enum Role {
        SYSADMIN,
        ADMIN,
        MANAGER,
        DISPATCHER,
        DRIVER,
        OWNER
    };
}