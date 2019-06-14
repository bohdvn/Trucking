package by.itechart.server.dto;

import by.itechart.server.entity.WayBill;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WayBillDto implements ToEntityTransformer {

    private Integer id;

    private WayBill.Status status;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private List<CheckpointDto> checkpoints;

    private InvoiceDto invoice;

    private WayBillDto() {
    }

    public static Builder builder() {
        return new WayBillDto().new Builder();
    }

    @Override
    public WayBill transformToEntity() {
        final WayBill wayBill = new WayBill();
        wayBill.setCheckpoints(this.checkpoints.stream()
                .map(CheckpointDto::transformToEntity).collect(Collectors.toList()));
        wayBill.setDateFrom(this.dateFrom);
        wayBill.setDateTo(this.dateTo);
        wayBill.setId(this.id);
        wayBill.setStatus(this.status);
        wayBill.setInvoice(this.invoice.transformToEntity());
        return null;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final int id) {
            WayBillDto.this.id = id;
            return this;
        }

        public Builder withCheckpoints(final List<CheckpointDto> checkpoints) {
            WayBillDto.this.checkpoints = checkpoints;
            return this;
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


        public Builder withInvoice(final InvoiceDto invoice) {
            WayBillDto.this.invoice = invoice;
            return this;
        }

        public WayBillDto build() {
            return WayBillDto.this;
        }
    }
}