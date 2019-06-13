package by.itechart.server.entity;

import java.util.List;

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

    public List<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(final List<User> recipients) {
        this.recipients = recipients;
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

}
