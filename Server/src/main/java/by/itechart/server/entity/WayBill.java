package by.itechart.server.entity;

import by.itechart.server.annotations.SearchCriteriaAnnotation;
import by.itechart.server.dto.WayBillDto;
import by.itechart.server.specifications.GetPathInterface;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "waybill")
public class WayBill implements ToDtoTransformer, GetPathInterface {
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "invoice_id")
    @SearchCriteriaAnnotation(path = "request car name; request driver surname; request driver name;" +
            " request driver patronymic")
    private Invoice invoice;

    @NotNull(message = "Date from cannot be null")
    @Column(name = "date_from")
    private LocalDate dateFrom;

    @NotNull(message = "Date to cannot be null")
    @Column(name = "date_to")
    private LocalDate dateTo;

    /**
     * In one wayBill may be several checkpoints.
     */
    @OneToMany(mappedBy = "wayBill", cascade =  CascadeType.ALL)
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