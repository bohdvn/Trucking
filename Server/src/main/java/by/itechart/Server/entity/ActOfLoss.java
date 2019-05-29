package by.itechart.Server.entity;

import by.itechart.Server.dto.ActOfLossDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "act_of_loss")
public class ActOfLoss implements Transformable {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Amount cannot be null")
    @Column(name = "amount")
    private Integer amount;

    /**
     *  In one actOfLoss may be several products.
     */
    @OneToMany( mappedBy = "actOfLoss", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Product> products;


    @Override
    public ActOfLossDto transform() {
        return ActOfLossDto.builder()
                .withAmount(this.amount)
                .build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
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
        ActOfLoss actOfLoss = (ActOfLoss) o;
        return Objects.equals(id, actOfLoss.id) &&
                Objects.equals(amount, actOfLoss.amount) &&
                Objects.equals(products, actOfLoss.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, products);
    }

    @Override
    public String toString() {
        return "ActOfLoss{" +
                "id=" + id +
                ", amount=" + amount +
                ", products=" + products +
                '}';
    }
}