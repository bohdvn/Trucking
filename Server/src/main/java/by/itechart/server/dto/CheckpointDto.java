package by.itechart.server.dto;

import by.itechart.server.entity.Checkpoint;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckpointDto implements ToEntityTransformer {

    private Integer id;

    private WayBillDto wayBill;

    private String name;

    private String latitude;

    private String longitude;

    private LocalDate date;

    private Checkpoint.Status status;

    private CheckpointDto() {
    }

    public static Builder builder() {
        return new CheckpointDto().new Builder();
    }

    @Override
    public Checkpoint transformToEntity() {
        final Checkpoint checkpoint = new Checkpoint();
        checkpoint.setDate(this.date);
        checkpoint.setId(this.id);
        checkpoint.setLatitude(this.latitude);
        checkpoint.setLongitude(this.longitude);
        checkpoint.setName(this.name);
        checkpoint.setStatus(this.status);
        //checkpoint.setWayBill(this.wayBill.transformToEntity());
        return checkpoint;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            CheckpointDto.this.id = id;
            return this;
        }

        public Builder withWayBill(final WayBillDto wayBill) {
            CheckpointDto.this.wayBill = wayBill;
            return this;
        }

        public Builder withName(final String name) {
            CheckpointDto.this.name = name;
            return this;
        }

        public Builder withLatitude(final String latitude) {
            CheckpointDto.this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(final String longitude) {
            CheckpointDto.this.longitude = longitude;
            return this;
        }

        public Builder withDate(final LocalDate date) {
            CheckpointDto.this.date = date;
            return this;
        }

        public Builder withStatus(final Checkpoint.Status status) {
            CheckpointDto.this.status = status;
            return this;
        }

        public CheckpointDto build() {
            return CheckpointDto.this;
        }
    }
}