package by.itechart.server.entity;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.transformers.ToDtoTransformer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "product")
public class Product implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Type cannot be null")
    @Size(min = 2, max = 45, message = "Type must be between 2 and 45 characters")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Amount cannot be null")
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "lost_amount")
    private Integer lostAmount;

    @NotNull(message = "Price cannot be null")
    @Column(name = "price")
    private Integer price;

    /**
     * Several products may be in the same request.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "request_id", updatable = false)
    private Request request;
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(name = "request_id")
//    private Request request;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Override
    public ProductDto transformToDto() {
        return ProductDto.builder()
                .withId(this.id)
                .withAmount(this.amount)
                .withName(this.name)
                .withPrice(this.price)
                .withStatus(this.status)
                .withType(this.type)
                .withLostAmount(this.lostAmount)
                //.withRequest(Optional.ofNullable(this.request).map(Request::transformToDto).orElse(null))
                .build();
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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Integer getLostAmount() {
        return lostAmount;
    }

    public void setLostAmount(Integer lostAmount) {
        this.lostAmount = lostAmount;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(type, product.type) &&
                Objects.equals(amount, product.amount) &&
                Objects.equals(lostAmount, product.lostAmount) &&
                Objects.equals(price, product.price) &&
                Objects.equals(request, product.request) &&
                status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, amount, lostAmount, price, request, status);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", lostAmount=" + lostAmount +
                ", price=" + price +
                ", request=" + request +
                ", status=" + status +
                '}';
    }

    public enum Status {
        REGISTERED,
        CHECKED,
        DELIVERED,
        LOST
    }
}