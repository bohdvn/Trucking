package by.itechart.server.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<User> recipients) {
        this.recipients = recipients;
    }
}
