package by.itechart.server.service.impl;

import by.itechart.server.entity.Email;
import by.itechart.server.entity.User;
import by.itechart.server.service.EmailSenderService;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
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
    public void send(final Email mail) {
        final List<User> recipients = mail.getRecipients();
        for (User user : recipients) {
            if (mail.getTemplate().equals("")) {
                sendEmail(user.getEmail(), mail.getSubject(), mail.getMessage());
            } else {
                sendPreparedMail(
                        user.getName(), mail.getBackgroundColor(), mail.getDate(), user.getEmail(),
                        mail.getSubject(), mail.getTemplate(), true);
            }
        }
    }

    private void sendPreparedMail(final String username, final String color, final LocalDate date, final String emailTo,
                                  final String subject, final String message, final Boolean isHtml) {
        final StringTemplateGroup group = new StringTemplateGroup("myGroup",
                getClass().getClassLoader().getResource("templates").getFile(), DefaultTemplateLexer.class);
        final StringTemplate stringTemplate = group.getInstanceOf("template");

        stringTemplate.setAttribute("name", username);
        stringTemplate.setAttribute("subject", subject);
        stringTemplate.setAttribute("color", color);
        stringTemplate.setAttribute("date", date);

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setText(stringTemplate.toString(), isHtml);
            javaMailSender.send(mail);
        } catch (Exception e) {
            LOGGER.error("Problem with sending email to: {}, error message: {}", emailTo, e.getMessage());
        }
    }
}
