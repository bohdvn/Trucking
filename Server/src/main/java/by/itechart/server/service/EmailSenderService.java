package by.itechart.server.service;

public interface EmailSenderService {

    void sendEmail(String emailTo, String subject, String message);
}
