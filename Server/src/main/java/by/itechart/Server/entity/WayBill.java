package by.itechart.Server.entity;

import by.itechart.Server.dto.WayBillDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "waybill")
public class WayBill implements Transformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Column(name = "name")
    private String name;

    /**
     * Several wayBills can be issued for one clientCompany.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id")
    private ClientCompany clientCompany;

    /**
     * One wayBill can have only one addressFrom.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "address_from_id")
    private Address addressFrom;

    /**
     * One wayBill can have only one addressTo.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "address_to_id")
    private Address addressTo;

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
    public WayBillDto transform() {
        return WayBillDto.builder()
                .withDateFrom(this.dateFrom)
                .withDateTo(this.dateTo)
                .withStatus(this.status)
                .build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public ClientCompany getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(final ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(final Address addressFrom) {
        this.addressFrom = addressFrom;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(final Address addressTo) {
        this.addressTo = addressTo;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(final LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(final LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayBill wayBill = (WayBill) o;
        return Objects.equals(id, wayBill.id) &&
                Objects.equals(status, wayBill.status) &&
                Objects.equals(name, wayBill.name) &&
                Objects.equals(clientCompany, wayBill.clientCompany) &&
                Objects.equals(addressFrom, wayBill.addressFrom) &&
                Objects.equals(addressTo, wayBill.addressTo) &&
                Objects.equals(invoice, wayBill.invoice) &&
                Objects.equals(dateFrom, wayBill.dateFrom) &&
                Objects.equals(dateTo, wayBill.dateTo) &&
                Objects.equals(checkpoints, wayBill.checkpoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, name, clientCompany, addressFrom, addressTo, invoice, dateFrom, dateTo, checkpoints);
    }

    @Override
    public String toString() {
        return "WayBill{" +
                "id=" + id +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", clientCompany=" + clientCompany +
                ", addressFrom=" + addressFrom +
                ", addressTo=" + addressTo +
                ", invoice=" + invoice +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", checkpoints=" + checkpoints +
                '}';
    }
}