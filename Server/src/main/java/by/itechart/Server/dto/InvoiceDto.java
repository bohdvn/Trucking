package by.itechart.Server.dto;

import by.itechart.Server.entity.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDto {

    private int id;

    private Invoice.Status status;

    private String number;

    private LocalDate dateOfIssue;

    private LocalDate dateOfCheck;

    private String driverFullName;

    private String carName;

    private String dispatcherFromFullName;

    private String dispatcherToFullName;

    private String managerFullName;

    private List<ProductDto> products = new ArrayList<>();

    private InvoiceDto(){}

    public static Builder builder() {
        return new InvoiceDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final int id){
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

        public Builder withCarName(final String carName){
            InvoiceDto.this.carName = carName;
            return  this;
        }

        public Builder withDriverFullName(String driverFullName){
            InvoiceDto.this.driverFullName = driverFullName;
            return this;
        }

        public Builder withDispathcerFromFullName(final String dispatcherFromFullName){
            InvoiceDto.this.dispatcherFromFullName = dispatcherFromFullName;
            return  this;
        }

        public Builder withDispatcherToFullName(final String dispatcherToFullName){
            InvoiceDto.this.dispatcherToFullName = dispatcherToFullName;
            return  this;
        }

        public Builder withManagerFullName(final String managerFullName){
            InvoiceDto.this.managerFullName = managerFullName;
            return  this;
        }

        public Builder withProducts(final List<ProductDto> products){
            InvoiceDto.this.products = products;
            return  this;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() { return carName; }

    public void setCarName(String carName) { this.carName = carName; }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(final List<ProductDto> products) {
        this.products = products;
    }

    public String getDriverFullName() { return driverFullName; }

    public void setDriverFullName(final String driverFullName) { this.driverFullName = driverFullName; }

    public String getDispatcherFromFullName() { return dispatcherFromFullName; }

    public void setDispatcherFromFullName(final String dispatcherFromFullName) { this.dispatcherFromFullName = dispatcherFromFullName; }

    public String getDispatcherToFullName() { return dispatcherToFullName; }

    public void setDispatcherToFullName(final String dispatcherToFullName) { this.dispatcherToFullName = dispatcherToFullName; }

    public String getManagerFullName() { return managerFullName; }

    public void setManagerFullName(final String managerFullName) { this.managerFullName = managerFullName; }
}