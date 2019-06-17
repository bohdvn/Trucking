package by.itechart.server.entity;

import by.itechart.server.dto.CheckpointDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Table(name = "checkpoint")
public class Checkpoint implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 255, message = "Latitude must be between 2 and 255 characters")
    @Column(name = "latitude")
    private String latitude;

    @Size(min = 2, max = 255, message = "Longitude must be between 2 and 255 characters")
    @Column(name = "longitude")
    private String longitude;

    @Column (name = "date")
    private LocalDate date;

    /**
     * Several checkpoints may be in the same wayBill.
     */
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "waybill_id")
    private WayBill wayBill;

    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Checkpoint.Status status;

    public enum Status {
        PASSED,
        NOT_PASSED
    }

    @Override
    public CheckpointDto transformToDto() {
        return CheckpointDto.builder()
                .withId(this.id)
                .withDate(this.date)
                .withLatitude(this.latitude)
                .withLongitude(this.longitude)
                .withName(this.name)
                .withStatus(this.status)
                .withWayBill(this.wayBill.transformToDto())
                .build();
    }
}