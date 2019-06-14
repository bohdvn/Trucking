package by.itechart.server.entity;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "waybill")
public class WayBill implements ToDtoTransformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @Column(name = "status")
    private Status status;

    /**
     * One waybill can only belong to one invoice.
     * In one invoice can be only one waybill.
     */
    @OneToOne(fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @NotNull(message = "Date from cannot be null")
    @Past(message = "Wrong date from")
    @Column(name = "date_from")
    private LocalDate dateFrom;

    @NotNull(message = "Date to cannot be null")
    @Past(message = "Wrong date to")
    @Column(name = "date_to")
    private LocalDate dateTo;

    /**
     * In one wayBill may be several checkpoints.
     */
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "wayBill",
            cascade =  CascadeType.ALL)
    private List<Checkpoint> checkpoints = new ArrayList<>();

    public enum Status {
        STARTED,
        FINISHED
    }

    @Override
    public WayBillDto transformToDto() {
        return WayBillDto.builder()
                .withId(this.id)
                .withCheckpoints(this.checkpoints.stream().map(Checkpoint::transformToDto).collect(Collectors.toList()))
                .withDateFrom(this.dateFrom)
                .withDateTo(this.dateTo)
                .withStatus(this.status)
                .withInvoice(this.invoice.transformToDto())
                .build();
    }
}