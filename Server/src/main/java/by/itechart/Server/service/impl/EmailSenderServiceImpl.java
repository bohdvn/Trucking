package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Email;
import by.itechart.Server.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

    private TemplateEngine templateEngine;

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    public EmailSenderServiceImpl() {

    }

    public EmailSenderServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmail(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setFrom(username);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    public void send(Email mail) {
        //get and fill the template
        final Context context = new Context();
        context.setVariable("message", mail.getMessage());
        String body = templateEngine.process("email/template", context);
        //send the html template
        sendPreparedMail(mail.getEmail(), mail.getObject(), body, true);
    }

    private void sendPreparedMail(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            javaMailSender.send(mail);
        } catch (Exception e) {
            LOGGER.error("Problem with sending email to: {}, error message: {}", to, e.getMessage());
        }
    }
}
