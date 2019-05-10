package by.itechart.Server.entity;

import by.itechart.Server.dto.ProductDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product implements Transformable {
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

    @NotNull(message = "Price cannot be null")
    @Column(name = "price")
    private Integer price;

    /**
     * Several products may be in the same invoice.
     */
    @ManyToOne(fetch=FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    /**
     * Several products may be in the same actOfLoss.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actOfLoss_id")
    private ActOfLoss actOfLoss;

    @Enumerated
    @Column(name = "status")
    private Status status;

    public enum Status {
        REGISTERED,
        CHECKED,
        DELIVERED,
        LOST
    }

    @Override
    public ProductDto transform() {
        return ProductDto.builder()
                .withId(this.id)
                .withAmount(this.amount)
                .withName(this.name)
                .withPrice(this.price)
                .withStatus(this.status)
                .withType(this.type)
                .withInvoiceId(this.invoice.getId())
                .withActOfLossId(this.actOfLoss.getId())
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
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
                Objects.equals(price, product.price) &&
                Objects.equals(invoice, product.invoice) &&
                Objects.equals(actOfLoss, product.actOfLoss) &&
                status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, amount, price, invoice, actOfLoss, status);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typePackiging='" + type + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", invoice=" + invoice +
                ", actOfLoss=" + actOfLoss +
                ", status=" + status +
                '}';
    }
}