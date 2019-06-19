package by.itechart.server.dto;

import by.itechart.server.entity.Invoice;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDto implements ToEntityTransformer {

    private Integer id;

    private Invoice.Status status;

    private String number;

    private LocalDate dateOfIssue;

    private LocalDate dateOfCheck;

    private UserDto dispatcherFrom;

    private UserDto dispatcherTo;

    private UserDto manager;

    private RequestDto request;


    private InvoiceDto() {
    }

    public static Builder builder() {
        return new InvoiceDto().new Builder();
    }

    @Override
    public Invoice transformToEntity() {
        final Invoice invoice = new Invoice();
        invoice.setDateOfCheck(this.dateOfCheck);
        invoice.setDateOfIssue(this.dateOfIssue);
        invoice.setDispatcherFrom(this.dispatcherFrom.transformToEntity());
        invoice.setId(this.id);
        invoice.setNumber(this.number);
        invoice.setRequest(this.request.transformToEntity());
        invoice.setStatus(this.status);
        invoice.setManager(this.manager != null ? this.manager.transformToEntity() : null);
        return invoice;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final int id) {
            InvoiceDto.this.id = id;
            return this;
        }

        public Builder withStatus(final Invoice.Status status) {
            InvoiceDto.this.status = status;
            return this;
        }

        public Builder withNumber(final String number) {
            InvoiceDto.this.number = number;
            return this;
        }

        public Builder withDateOfIssue(final LocalDate dateOfIssue) {
            InvoiceDto.this.dateOfIssue = dateOfIssue;
            return this;
        }

        public Builder withDateOfCheck(final LocalDate dateOfCheck) {
            InvoiceDto.this.dateOfCheck = dateOfCheck;
            return this;
        }

        public Builder withDispatcherFrom(final UserDto dispatcherFrom) {
            InvoiceDto.this.dispatcherFrom = dispatcherFrom;
            return this;
        }

        public Builder withDispatcherTo(final UserDto dispatcherTo) {
            InvoiceDto.this.dispatcherTo = dispatcherTo;
            return this;
        }

        public Builder withManager(final UserDto manager) {
            InvoiceDto.this.manager = manager;
            return this;
        }

        public Builder withRequest(final RequestDto request) {
            InvoiceDto.this.request = request;
            return this;
        }

        public InvoiceDto build() {
            return InvoiceDto.this;
        }
    }

}