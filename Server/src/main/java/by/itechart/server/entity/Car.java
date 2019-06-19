package by.itechart.server.entity;

import by.itechart.server.annotations.SearchCriteriaAnnotation;
import by.itechart.server.dto.CarDto;
import by.itechart.server.specifications.GetPathInterface;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "car")
public class Car implements ToDtoTransformer, GetPathInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @NotNull(message = "Car type cannot be null")
    @Column(name = "car_type")
    private CarType carType;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    @Column(name = "name")
    @SearchCriteriaAnnotation
    private String name;

    @NotNull(message = "Consumption cannot be null")
    @Column(name = "consumption")
    private Integer consumption;

    @Enumerated
    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    /**
     * One car can be in different requests in various dates.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Request> requests;

    @Override
    public CarDto transformToDto() {
        return CarDto.builder()
                .withId(this.id)
                .withCarType(this.carType)
                .withConsumption(this.consumption)
                .withName(this.name)
                .withStatus(this.status)
                .build();
    }

    public enum Status {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum CarType {
        TILT, FRIDGE, TANKER
    }
}