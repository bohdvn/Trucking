package by.itechart.server.entity;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Email {
    @NotNull
    private List<User> recipients;
    @NotNull
    private String subject;
    @NotNull
    private String message;
    @NotNull
    private String object;
//    @NotNull
//    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.
//    [a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
//    private String email;

    public Email() {
    }

    public Email(@NotNull List<User> recipients, @NotNull String subject, @NotNull String message, @NotNull String object) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.object = object;
    }
}
