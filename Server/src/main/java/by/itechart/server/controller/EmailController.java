package by.itechart.server.controller;

import by.itechart.server.entity.Email;
import by.itechart.server.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    private EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @Transactional
    @PostMapping("")
    public ResponseEntity<?> sendEmail(final @RequestBody Email email) {
        LOGGER.info("REST request. Path:/email method: POST. email: {}", email);
        emailSenderService.send(email);
        LOGGER.info("Success!Email was sent.");
        return ResponseEntity.status(HttpStatus.OK)
                .body("Success!Email was sent");
    }
}
