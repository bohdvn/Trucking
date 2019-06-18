package by.itechart.server.entity;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.dto.RequestDto;
import by.itechart.server.transformers.ToDtoTransformer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gorlach Dmitry
 */

@Entity
@Table(name = "request")
public class Request implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * One request can have one car.
     * The same car can be in different requests in various dates.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * One request can have one driver.
     * The same driver can be in different requests in various dates.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private User driver;

    /**
     * One request can have only one clientCompanyFrom.
     * The same clientCompanyFrom can be in different requests in various dates.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_from")
    private ClientCompany clientCompanyFrom;

    /**
     * One request can have only one clientCompanyTo.
     * The same clientCompanyTo can be in different requests in various dates.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_to")
    private ClientCompany clientCompanyTo;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    @Override
    public RequestDto transformToDto() {
        final List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: this.products) {
            productDtos.add(product.transformToDto());
        }
        return RequestDto.builder()
                .withId(this.id)
                .withCar(this.car.transformToDto())
                .withClientCompanyFrom(this.clientCompanyFrom.transformToDto())
                .withClientCompanyTo(this.clientCompanyTo.transformToDto())
                .withDriver(this.driver.transformToDto())
                .withStatus(this.status)
                .withProducts(productDtos)
                .build();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(final User driver) {
        this.driver = driver;
    }

    public ClientCompany getClientCompanyFrom() {
        return clientCompanyFrom;
    }

    public void setClientCompanyFrom(final ClientCompany clientCompanyFrom) {
        this.clientCompanyFrom = clientCompanyFrom;
    }

    public ClientCompany getClientCompanyTo() {
        return clientCompanyTo;
    }

    public void setClientCompanyTo(final ClientCompany clientCompanyTo) {
        this.clientCompanyTo = clientCompanyTo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(car, request.car) &&
                Objects.equals(driver, request.driver) &&
                Objects.equals(clientCompanyFrom, request.clientCompanyFrom) &&
                Objects.equals(clientCompanyTo, request.clientCompanyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, driver, clientCompanyFrom, clientCompanyTo);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", car=" + car +
                ", driver=" + driver +
                ", clientCompanyFrom=" + clientCompanyFrom +
                ", clientCompanyTo=" + clientCompanyTo +
                '}';
    }

    public enum Status {
        NOT_VIEWED,
        REJECTED,
        ISSUED
    }
}
