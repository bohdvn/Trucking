package by.itechart.server.service;

import by.itechart.server.entity.Email;

public interface EmailSenderService {

    void sendEmail(final String emailTo, final String subject, final String message);

    void send(final Email email);
}
