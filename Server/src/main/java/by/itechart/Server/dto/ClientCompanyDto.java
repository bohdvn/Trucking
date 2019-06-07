package by.itechart.Server.dto;

import by.itechart.Server.entity.ClientCompany;

import java.util.List;

public class ClientCompanyDto {
    private int id;

    private String name;

    private ClientCompany.Status status;

    private ClientCompany.Type type;

    private AddressDto address;

    private List<UserDto> users;

    private ClientCompanyDto() {
    }

    public static Builder builder() {
        return new ClientCompanyDto().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder withId(final int id){
            ClientCompanyDto.this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            ClientCompanyDto.this.name = name;
            return this;
        }

        public Builder withAddress(final AddressDto address) {
            ClientCompanyDto.this.address = address;
            return this;
        }

        public Builder withUsers(final List<UserDto> users) {
            ClientCompanyDto.this.users = users;
            return this;
        }

        public Builder withStatus(final ClientCompany.Status status){
            ClientCompanyDto.this.status = status;
            return this;
        }

        public Builder withType(final ClientCompany.Type type){
            ClientCompanyDto.this.type = type;
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

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public ClientCompany.Status getStatus() {
        return status;
    }

    public void setStatus(final ClientCompany.Status status) {
        this.status = status;
    }

    public ClientCompany.Type getType() {
        return type;
    }

    public void setType(final ClientCompany.Type type) {
        this.type = type;
    }
}