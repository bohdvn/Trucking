package by.itechart.server.service.impl;

import by.itechart.server.entity.Email;
import by.itechart.server.entity.User;
import by.itechart.server.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmail(final String emailTo, final String subject, final String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailTo);
            mailMessage.setFrom(username);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            LOGGER.error("Problem with sending email to: {}, error message: {}", emailTo, e.getMessage());
        }
    }

    @Async
    @Override
    public void send(Email mail) {
        final List<User> recipients = mail.getRecipients();
        for (User user : recipients) {
            if (mail.getObject().equals("")) {
                sendEmail(user.getEmail(), mail.getSubject(), mail.getMessage());
            } else {
                sendPreparedMail(user.getEmail(), mail.getSubject(), mail.getObject(), true);
            }
        }
    }

    private void sendPreparedMail(String emailTo, String subject, String message, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setText(message, isHtml);
            javaMailSender.send(mail);
        } catch (Exception e) {
            LOGGER.error("Problem with sending email to: {}, error message: {}", emailTo, e.getMessage());
        }
    }
}
