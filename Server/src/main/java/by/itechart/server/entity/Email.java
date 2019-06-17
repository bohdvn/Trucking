package by.itechart.server.entity;

import lombok.Data;

import java.util.List;

@Data
public class Email {

    private List<User> recipients;

    private String subject;

    private String message;

    public Email() {
    }

    public Email(List<User> recipients, String subject, String message) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
    }
}
