package by.itechart.Server.dto;

import java.time.LocalDate;

public class CheckpointDto {

    private String name;

    private String latitude;

    private String longitude;

    private LocalDate date;

    private CheckpointDto(){}

    public static Builder builder() {
        return new CheckpointDto().new Builder();
    }

    public class Builder {
        private Builder() {
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

        public CheckpointDto build() {
            return CheckpointDto.this;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }
}