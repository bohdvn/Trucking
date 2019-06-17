package by.itechart.server.entity;

import by.itechart.server.dto.AddressDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@Entity
@Table(name = "address")
public class Address implements ToDtoTransformer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 150, message = "City must be between 2 and 150 characters")
    @Column(name = "city")
    private String city;

    @Size(min = 2, max = 150, message = "Street must be between 2 and 150 characters")
    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private Integer building;

    @Column(name = "flat")
    private Integer flat;

    @Size(min = 2, max = 255, message = "Latitude must be between 2 and 255 characters")
    @Column(name = "latitude")
    private String latitude;

    @Size(min = 2, max = 255, message = "Longitude must be between 2 and 150 characters")
    @Column(name = "longitude")
    private String longitude;

    @Override
    public AddressDto transformToDto() {
        return AddressDto.builder()
                .withId(this.id)
                .withCity(this.city)
                .withStreet(this.street)
                .withBuilding(this.building)
                .withFlat(this.flat)
                .build();
    }
}