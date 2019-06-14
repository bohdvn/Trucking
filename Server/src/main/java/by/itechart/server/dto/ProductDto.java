package by.itechart.server.dto;

import by.itechart.server.entity.Product;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

@Data
public class ProductDto implements ToEntityTransformer {
    private Integer id;

    private String name;

    private String type;

    private Integer amount;

    private Integer price;

    private Product.Status status;

    private int lostAmount;

    private RequestDto request;

    private ProductDto() {
    }

    public static Builder builder() {
        return new ProductDto().new Builder();
    }

    @Override
    public Product transformToEntity() {
        final Product product = new Product();
        //product.setRequest(this.request.transformToEntity());
        product.setAmount(this.amount);
        product.setId(this.id);
        product.setLostAmount(this.lostAmount);
        product.setName(this.name);
        product.setPrice(this.price);
        product.setStatus(this.status);
        product.setType(this.type);
        return product;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withRequest(final RequestDto request) {
            ProductDto.this.request = request;
            return this;
        }

        public Builder withId(final int id) {
            ProductDto.this.id = id;
            return this;
        }

        public Builder withLostAmount(final int lostAmount) {
            ProductDto.this.lostAmount = lostAmount;
            return this;
        }


        public Builder withName(final String name) {
            ProductDto.this.name = name;
            return this;
        }

        public Builder withType(final String type) {
            ProductDto.this.type = type;
            return this;
        }

        public Builder withAmount(final Integer amount) {
            ProductDto.this.amount = amount;
            return this;
        }

        public Builder withPrice(final Integer price) {
            ProductDto.this.price = price;
            return this;
        }

        public Builder withStatus(final Product.Status status) {
            ProductDto.this.status = status;
            return this;
        }

        public ProductDto build() {
            return ProductDto.this;
        }
    }

}