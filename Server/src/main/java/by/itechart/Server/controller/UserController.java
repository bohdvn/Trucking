package by.itechart.Server.controller;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.ConfirmationToken;
import by.itechart.Server.entity.User;
import by.itechart.Server.security.*;
import by.itechart.Server.service.ConfirmationTokenService;
import by.itechart.Server.service.EmailSenderService;
import by.itechart.Server.service.UserService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private ConfirmationTokenService confirmationTokenService;

    private EmailSenderService emailSenderService;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal userPrincipal,@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/user{} method: GET.", id);
        final UserDto userDto = userService.findById(id);
        return Objects.nonNull(userDto) ?
                ResponseEntity.ok().body(userDto) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<UserDto>> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<UserDto> users = userService.findAll(pageable);
        return users.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/drivers")
    public ResponseEntity<?> getDrivers() {
        LOGGER.info("REST request. Path:/user method: GET.");
        final List<UserDto> users = userService.findAll();
        return
              //  users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('SYSADMIN')")
    @Transactional
    @PostMapping("")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal,@Valid @RequestBody UserDto user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        UserDto existingUser = userService.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error. This email already exists!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user.transformToEntity());
            confirmationTokenService.save(confirmationToken.transformToDto());
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @Transactional
    @PutMapping("/")
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal userPrincipal,@Valid @RequestBody UserDto user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{selectedUsers}")
    public ResponseEntity<?> remove(@CurrentUser UserPrincipal userPrincipal,@PathVariable("selectedUsers") String selectedUsers ) {
        LOGGER.info("REST request. Path:/user/{} method: DELETE.", selectedUsers);
        final String delimeter = ",";
        final String[] usersId = selectedUsers.split(delimeter);
        for (String id : usersId) {
            userService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/confirm-account/{confirmationToken}")
    public ResponseEntity<?> confirmUserAccount(@PathVariable String confirmationToken) {
        final ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken).transformToEntity();
        if (token != null) {
            final UserDto user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
            if (!user.getEnabled()) {
                user.setEnabled(true);
                userService.save(user);
                LOGGER.info("Users field isEnabled was change.");
                confirmationTokenService.delete(token.transformToDto());
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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse response=new JwtAuthenticationResponse(jwt);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
