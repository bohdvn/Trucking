package by.itechart.server.entity;

import by.itechart.server.dto.ClientCompanyDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "client_company")
public class ClientCompany implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    @Column(name = "name")
    private String name;

    @Enumerated
    @NotNull(message = "Type cannot be null")
    @Column(name = "type")
    private Type type;

    @Enumerated
    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    /**
     * One clientCompany can have only one address.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

    @OneToMany(mappedBy = "clientCompany", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public ClientCompanyDto transformToDto() {
        return ClientCompanyDto.builder()
                .withId(this.id)
                .withName(this.name)
                .withType(this.type)
                .withStatus(this.status)
                .withAddress(this.address.transformToDto())
//                .withUsers(this.users.stream().map(User::transformToDto).collect(Collectors.toList()))
                .build();
    }

    public enum Type {
        LEGAL, INDIVIDUAL
    }

    public enum Status {
        ACTIVE, BLOCKED
    }
}