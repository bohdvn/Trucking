package by.itechart.Server.dto;

import by.itechart.Server.entity.Car;

public class CarDto {

    private Car.CarType carType;

    private String name;

    private Integer consumption;

    private Car.Status status;

    private CarDto(){}

    public static Builder builder() {
        return new CarDto().new Builder();
    }

    public class Builder {
        private Builder() {
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

    public Car.CarType getCarType() {
        return carType;
    }

    public void setCarType(final Car.CarType carType) {
        this.carType = carType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(final Integer consumption) {
        this.consumption = consumption;
    }

    public Car.Status getStatus() {
        return status;
    }

    public void setStatus(final Car.Status status) {
        this.status = status;
    }
}