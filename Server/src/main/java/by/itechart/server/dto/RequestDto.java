package by.itechart.server.dto;

import by.itechart.server.entity.Request;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RequestDto implements ToEntityTransformer {
    private Integer id;

    private Request.Status status;

    private CarDto car;

    private UserDto driver;

    private ClientCompanyDto clientCompanyFrom;

    private AddressDto address;

    private List<ProductDto> products;

    public static Builder builder() {
        return new RequestDto().new Builder();
    }

    @Override
    public Request transformToEntity() {
        final Request request = new Request();
        request.setClientCompanyFrom(this.clientCompanyFrom.transformToEntity());
        request.setAddress(this.address.transformToEntity());
        request.setDriver(this.driver.transformToEntity());
        request.setCar(this.car.transformToEntity());
        request.setStatus(this.status);
        request.setProducts(this.products.stream().map(ProductDto::transformToEntity).collect(Collectors.toList()));
        request.setId(this.id);
        return request;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final int id) {
            RequestDto.this.id = id;
            return this;
        }

        public Builder withStatus(final Request.Status status) {
            RequestDto.this.status = status;
            return this;
        }

        public Builder withCar(final CarDto car) {
            RequestDto.this.car = car;
            return this;
        }

        public Builder withDriver(final UserDto driver) {
            RequestDto.this.driver = driver;
            return this;
        }

        public Builder withClientCompanyFrom(final ClientCompanyDto clientCompanyFrom) {
            RequestDto.this.clientCompanyFrom = clientCompanyFrom;
            return this;
        }

        public Builder withAddress(final AddressDto address) {
            RequestDto.this.address = address;
            return this;
        }

        public Builder withProducts(final List<ProductDto> products) {
            RequestDto.this.products = products;
            return this;
        }

        public RequestDto build() {
            return RequestDto.this;
        }
    }
}
