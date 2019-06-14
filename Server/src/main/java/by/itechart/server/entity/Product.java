package by.itechart.server.entity;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.transformers.ToDtoTransformer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Data
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

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
    public ProductDto transformToDto() {
        return ProductDto.builder()
                .withId(this.id)
                .withAmount(this.amount)
                .withName(this.name)
                .withPrice(this.price)
                .withStatus(this.status)
                .withType(this.type)
                .withLostAmount(this.lostAmount)
                //              .withRequest(this.request != null? this.request.transform() : RequestDto.builder().build())
                .build();
    }
}