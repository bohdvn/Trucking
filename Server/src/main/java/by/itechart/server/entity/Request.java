package by.itechart.server.entity;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.dto.RequestDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gorlach Dmitry
 */

@Data
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
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "address_to")
    private Address address;

    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Override
    public RequestDto transformToDto() {
        final List<Product> products = this.products;
        final List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            productDtos.add(product.transformToDto());
        }
        return RequestDto.builder()
                .withId(this.id)
                .withCar(this.car.transformToDto())
                .withClientCompanyFrom(this.clientCompanyFrom.transformToDto())
                .withAddress(this.address.transformToDto())
                .withDriver(this.driver.transformToDto())
                .withStatus(this.status)
                .withProducts(productDtos)
                .build();
    }


    public enum Status {
        NOT_VIEWED,
        REJECTED,
        ISSUED
    }
}
