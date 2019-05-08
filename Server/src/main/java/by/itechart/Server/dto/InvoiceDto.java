package by.itechart.Server.dto;

import by.itechart.Server.entity.Invoice;

import java.time.LocalDate;

public class InvoiceDto {

    private Invoice.Status status;

    private String number;

    private LocalDate dateOfIssue;

    private LocalDate dateOfCheck;

    private InvoiceDto(){}

    public static Builder builder() {
        return new InvoiceDto().new Builder();
    }

    public class Builder {
        private Builder() {
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

        public InvoiceDto build() {
            return InvoiceDto.this;
        }
    }

    public Invoice.Status getStatus() {
        return status;
    }

    public void setStatus(final Invoice.Status status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(final LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(final LocalDate dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }
}