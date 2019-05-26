package by.itechart.Server.controller;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.ConfirmationToken;
import by.itechart.Server.entity.User;
import by.itechart.Server.service.ConfirmationTokenService;
import by.itechart.Server.service.EmailSenderService;
import by.itechart.Server.service.UserService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private ConfirmationTokenService confirmationTokenService;
    private EmailSenderService emailSenderService;
    private UserService userService;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/user{} method: GET.", id);
        Optional<User> user = userService.findById(id);
        return user.isPresent() ?
                ResponseEntity.ok().body(user.get().transform()) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<User> users = userService.findAll(pageable);
        Page<UserDto> usersDto = new PageImpl<>(users.stream().map(User::transform)
                .sorted(Comparator.comparing(UserDto::getSurname))
                .collect(Collectors.toList()), pageable, users.getTotalElements());
        LOGGER.info("Return userList.size:{}", usersDto.getNumber());
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        User existingUser = userService.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error. This email already exists!");
        } else {
            userService.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenService.save(confirmationToken);
            String message = String.format("Hello, %s! To confirm your account, " +
                            "please visit next link: http://localhost:8080/user/confirm-account/%s",
                    user.getName(), confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(user.getEmail(), "Complete Registration!", message);
            LOGGER.info("Success.Confirmation email was sent.");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/")
    public ResponseEntity<?> edit(@Valid @RequestBody User user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm-account/{confirmationToken}")
    public ResponseEntity<?> confirmUserAccount(@PathVariable String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
            if (!user.getEnabled()) {
                user.setEnabled(true);
                userService.save(user);
                LOGGER.info("Users field isEnabled was change.");
                confirmationTokenService.delete(token);
                LOGGER.info("Confirmation token was deleted.");
            }

        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error. The link is invalid or your account has been already confirmed.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success! Account verified.");
    }
}
