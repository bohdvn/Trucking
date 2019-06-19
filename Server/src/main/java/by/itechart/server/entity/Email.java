package by.itechart.server.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    private String template;
    @NotNull
    private String backgroundColor;
    @NotNull
    private LocalDate date;

    public Email() {
    }

    public Email(@NotNull List<User> recipients, @NotNull String subject, @NotNull String message,
                 @NotNull String template, @NotNull String backgroundColor, @NotNull LocalDate date) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.template = template;
        this.backgroundColor = backgroundColor;
        this.date = date;
    }
}
