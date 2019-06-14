package by.itechart.server.dto;

import by.itechart.server.annotations.CriteriaAnnotation;
import by.itechart.server.entity.Car;
import by.itechart.server.interfaces.FieldsInterface;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

@Data
public class CarDto implements ToEntityTransformer, FieldsInterface {
    private Integer id;

    private Car.CarType carType;

    @CriteriaAnnotation
    private String name;

    private Integer consumption;

    private Car.Status status;

    // private List<RequestDto> requests;

    private CarDto() {
    }

    public static Builder builder() {
        return new CarDto().new Builder();
    }

    @Override
    public Car transformToEntity() {
        final Car car = new Car();
        car.setId(this.id);
        car.setCarType(this.carType);
        car.setConsumption(this.consumption);
        car.setName(this.name);
        car.setStatus(this.status);
        return car;
    }


    public class Builder {
        private Builder() {
        }

//        public Builder withRequests(final List<RequestDto> requests) {
//            CarDto.this.requests = requests;
//            return this;
//        }

        public Builder withId(final int id) {
            CarDto.this.id = id;
            return this;
        }

        public Builder withCarType(final Car.CarType carType) {
            CarDto.this.carType = carType;
            return this;
        }

        public Builder withName(final String name) {
            CarDto.this.name = name;
            return this;
        }

        public Builder withConsumption(final Integer consumption) {
            CarDto.this.consumption = consumption;
            return this;
        }

        public Builder withStatus(final Car.Status status) {
            CarDto.this.status = status;
            return this;
        }

        public CarDto build() {
            return CarDto.this;
        }
    }
}