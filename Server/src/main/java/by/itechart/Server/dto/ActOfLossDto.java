package by.itechart.Server.dto;

public class ActOfLossDto {

    private Integer amount;

    private ActOfLossDto(){}

    public static Builder builder() {
        return new ActOfLossDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withAmount(final Integer amount) {
            ActOfLossDto.this.amount = amount;
            return this;
        }

        public ActOfLossDto build() {
            return ActOfLossDto.this;
        }
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
}