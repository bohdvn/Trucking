package by.itechart.server.entity;

import by.itechart.server.annotations.SearchCriteriaAnnotation;
import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.specifications.GetPathInterface;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "invoice")
public class Invoice implements ToDtoTransformer, GetPathInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private Status status;

    @NotNull(message = "Number cannot be null")
    @Column(name = "number")
    private String number;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "date_of_check")
    private LocalDate dateOfCheck;

    /**
     * One invoice can be issued by one dispatcherFrom.
     * The same dispatcherFrom can be in different invoices in various dates.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatcher_from_id")
    private User dispatcherFrom;

    /**
     * One invoice can be checked by one manager.
     * The same manager can check many different invoices.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    /**
     * One invoice can have only one request.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "request_id")
    @SearchCriteriaAnnotation(path = "car name; driver surname; driver name; driver patronymic")
    private Request request;

    @Override
    public InvoiceDto transformToDto() {
        return InvoiceDto.builder()
                .withId(this.id)
                .withDateOfCheck(this.dateOfCheck)
                .withDateOfIssue(this.dateOfIssue)
                .withNumber(this.number)
                .withStatus(this.status)
                .withDispatcherFrom((this.dispatcherFrom == null) ? null : this.dispatcherFrom.transformToDto())
                .withManager((this.manager == null) ? null : this.manager.transformToDto())
                .withRequest(this.request.transformToDto())
                .build();
    }

    public enum Status {
        COMPLETED,
        CHECKED,
        CHECKED_BY_DRIVER,
        DELIVERED
    }
}