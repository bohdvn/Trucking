package by.itechart.server.entity;

import by.itechart.server.dto.AddressDto;
import by.itechart.server.transformers.ToDtoTransformer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() { return city; }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(final Integer building) {
        this.building = building;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(final Integer flat) {
        this.flat = flat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(building, address.building) &&
                Objects.equals(flat, address.flat) &&
                Objects.equals(latitude, address.latitude) &&
                Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, building, flat, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building=" + building +
                ", flat=" + flat +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}