package by.itechart.Server.dto;

import by.itechart.Server.entity.WayBill;

import java.time.LocalDate;

public class WayBillDto {

    private WayBill.Status status;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private AddressDto addressFrom;

    private AddressDto addressTo;

    private InvoiceDto invoice;

    private WayBillDto(){}

    public static Builder builder() {
        return new WayBillDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withStatus(final WayBill.Status status) {
            WayBillDto.this.status = status;
            return this;
        }

        public Builder withDateFrom(final LocalDate dateFrom) {
            WayBillDto.this.dateFrom = dateFrom;
            return this;
        }

        public Builder withDateTo(final LocalDate dateTo) {
            WayBillDto.this.dateTo = dateTo;
            return this;
        }

        public Builder withAddressFrom(final AddressDto addressFrom) {
            WayBillDto.this.addressFrom = addressFrom;
            return this;
        }

        public Builder withAddressTo(final AddressDto addressTo) {
            WayBillDto.this.addressTo = addressTo;
            return this;
        }

        public Builder withInvoice(final InvoiceDto invoice) {
            WayBillDto.this.invoice = invoice;
            return this;
        }

        public WayBillDto build() {
            return WayBillDto.this;
        }
    }

    public WayBill.Status getStatus() {
        return status;
    }

    public void setStatus(final WayBill.Status status) {
        this.status = status;
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

    public AddressDto getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(AddressDto addressFrom) {
        this.addressFrom = addressFrom;
    }

    public AddressDto getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(AddressDto addressTo) {
        this.addressTo = addressTo;
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }
}