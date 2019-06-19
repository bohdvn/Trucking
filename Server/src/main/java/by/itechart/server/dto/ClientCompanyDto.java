package by.itechart.server.dto;

import by.itechart.server.entity.ClientCompany;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientCompanyDto implements ToEntityTransformer {

    private Integer id;

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

    @Override
    public ClientCompany transformToEntity() {
        final ClientCompany clientCompany = new ClientCompany();
        clientCompany.setAddress(this.address.transformToEntity());
        clientCompany.setId(this.id);
        clientCompany.setName(this.name);
        clientCompany.setStatus(this.status);
        clientCompany.setType(this.type);
        clientCompany.setUsers(this.users!=null?
                this.users.stream().map(UserDto::transformToEntity).collect(Collectors.toList()):null);
        return clientCompany;
    }

    public class Builder {

        private Builder() {
        }

        public Builder withId(final int id) {
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

        public Builder withStatus(final ClientCompany.Status status) {
            ClientCompanyDto.this.status = status;
            return this;
        }

        public Builder withType(final ClientCompany.Type type) {
            ClientCompanyDto.this.type = type;
            return this;
        }


        public ClientCompanyDto build() {
            return ClientCompanyDto.this;
        }
    }
}