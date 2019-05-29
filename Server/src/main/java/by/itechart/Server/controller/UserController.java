package by.itechart.Server.controller;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.ConfirmationToken;
import by.itechart.Server.entity.User;
import by.itechart.Server.security.*;
import by.itechart.Server.service.ConfirmationTokenService;
import by.itechart.Server.service.EmailSenderService;
import by.itechart.Server.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
    private ConfirmationTokenService confirmationTokenService;
    private EmailSenderService emailSenderService;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/user{} method: GET.", id);
        Optional<User> user = userService.findById(id);
        return user.isPresent()?
                ResponseEntity.ok().body(user.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<UserDto>> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<User> users = userService.findAll(pageable);
        Page<UserDto> usersDto = new PageImpl<>(users.stream().map(User::transform)
                .sorted(Comparator.comparing(UserDto :: getSurname))
                .collect(Collectors.toList()), pageable, users.getTotalElements());
        LOGGER.info("Return userList.size:{}", usersDto.getNumber());
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @Transactional
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody User user){
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        User existingUser = userService.findByEmailIgnoreCase(user.getEmail());
        if(existingUser != null){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error. This email already exists!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
//            ConfirmationToken confirmationToken = new ConfirmationToken(user);
//            confirmationTokenService.save(confirmationToken);
//            String message = String.format("Hello, %s! To confirm your account, " +
//                            "please visit next link: http://localhost:8080/user/confirm-account/%s",
//                            user.getName(), confirmationToken.getConfirmationToken());
//
//            emailSenderService.sendEmail(user.getEmail(),"Complete Registration!", message);
            LOGGER.info("Success.Confirmation email was sent.");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @Transactional
    @PutMapping("/")
    public ResponseEntity<?> edit(@RequestBody User user){
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm-account/{confirmationToken}")
    public ResponseEntity<?> confirmUserAccount(@PathVariable String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
            if(!user.getEnabled()) {
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
