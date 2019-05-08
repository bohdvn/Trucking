package by.itechart.Server.dto;

public class ClientCompanyDto {

    private String name;

    private ClientCompanyDto() {
    }

    public static Builder builder() {
        return new ClientCompanyDto().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder withName(final String name) {
            ClientCompanyDto.this.name = name;
            return this;
        }

        public ClientCompanyDto build() {
            return ClientCompanyDto.this;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}