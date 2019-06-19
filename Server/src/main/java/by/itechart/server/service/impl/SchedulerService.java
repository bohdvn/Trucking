package by.itechart.server.service.impl;

import by.itechart.server.entity.User;
import by.itechart.server.service.EmailSenderService;
import by.itechart.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class SchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private static final String CRON = "*0 0 8 * * *";
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        List<User> list = userService.getAllByBirthday(month, day);
        if (!list.isEmpty()) {
            list.forEach(user -> {
                try {
                    String message = "С Днем Рождения, уважаемый " + user.getName() + "!";
                    emailSenderService.sendEmail(user.getEmail(), "С Днем Рождения!", message);
                    LOGGER.info("Email have been sent. User id: {}, Date: {}", user.getId(), date);
                } catch (Exception e) {
                    LOGGER.error("Email can't be sent. User's id: {}, Error: {}", user.getId(), e.getMessage());
                    LOGGER.error("Email can't be sent.", e);
                }
            });
        }
    }
}

