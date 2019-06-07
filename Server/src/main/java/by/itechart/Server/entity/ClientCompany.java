package by.itechart.Server.entity;

import by.itechart.Server.dto.ClientCompanyDto;

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
@Table(name = "client_company")
public class ClientCompany implements Transformable {
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
    public ClientCompanyDto transform() {
        return ClientCompanyDto.builder()
                .withId(this.id)
                .withName(this.name)
                .withType(this.type)
                .withStatus(this.status)
                .withAddress(this.address.transform())
//                .withUsers(this.users.stream().map(User::transform).collect(Collectors.toList()))
                .build();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCompany that = (ClientCompany) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                type == that.type &&
                status == that.status &&
                Objects.equals(address, that.address) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, status, address, users);
    }

    @Override
    public String toString() {
        return "ClientCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", address=" + address +
                ", users=" + users +
                '}';
    }

    public enum Type {
        LEGAL, INDIVIDUAL
    }

    public enum Status {
        ACTIVE, BLOCKED
    }
}