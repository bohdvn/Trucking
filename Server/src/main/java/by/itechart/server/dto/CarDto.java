package by.itechart.server.dto;

import by.itechart.server.entity.Car;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

@Data
public class CarDto implements ToEntityTransformer {
    private Integer id;

    private Car.CarType carType;

    private String name;

    private Integer consumption;

    private Car.Status status;

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