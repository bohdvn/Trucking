package by.itechart.server.dto;

import by.itechart.server.entity.ConfirmationToken;
import by.itechart.server.transformers.ToEntityTransformer;
import lombok.Data;

import java.util.Date;

@Data
public class ConfirmationTokenDto implements ToEntityTransformer {

    private Integer id;

    private String confirmationToken;

    private Date createDate;

    private UserDto user;

    public static Builder builder() {
        return new ConfirmationTokenDto().new Builder();
    }

    @Override
    public ConfirmationToken transformToEntity() {
        final ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmationToken(this.confirmationToken);
        confirmationToken.setCreateDate(this.createDate);
        confirmationToken.setId(this.id);
        //confirmationToken.setUser(this.user.transformToEntity());
        return confirmationToken;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final int id) {
            ConfirmationTokenDto.this.id = id;
            return this;
        }

        public Builder withUser(final UserDto user) {
            ConfirmationTokenDto.this.user = user;
            return this;
        }

        public Builder withCreateDate(final Date createDate) {
            ConfirmationTokenDto.this.createDate = createDate;
            return this;
        }

        public Builder withConfirmationToken(final String confirmationToken) {
            ConfirmationTokenDto.this.confirmationToken = confirmationToken;
            return this;
        }


        public ConfirmationTokenDto build() {
            return ConfirmationTokenDto.this;
        }
    }
}
