package by.itechart.server.controller;

import by.itechart.server.dto.ClientCompanyDto;
import by.itechart.server.dto.UserDto;
import by.itechart.server.entity.ClientCompany;
import by.itechart.server.entity.ConfirmationToken;
import by.itechart.server.entity.User;
import by.itechart.server.security.*;
import by.itechart.server.service.ClientCompanyService;
import by.itechart.server.service.ConfirmationTokenService;
import by.itechart.server.service.EmailSenderService;
import by.itechart.server.service.UserService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientCompanyService clientCompanyService;

    private UserService userService;

    private ConfirmationTokenService confirmationTokenService;

    private EmailSenderService emailSenderService;

    public UserController(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    @PreAuthorize("hasAuthority('SYSADMIN') or hasAuthority('OWNER') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal userPrincipal, final @PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/user{} method: GET.", id);
        UserDto userDto = userService.findById(id);
        if(Objects.nonNull(userDto)){

            User user = userDto.transformToEntity();
            ClientCompany clientCompany = user.getClientCompany();
            if (clientCompany != null && !userPrincipal.getClientCompanyId().equals(clientCompany.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.ok().body(userDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<UserDto>> getAll(@CurrentUser UserPrincipal userPrincipal, final Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<UserDto> users = userService.findAllByClientCompanyId(userPrincipal.getClientCompanyId(), pageable);
        LOGGER.info("Return userList.size:{}", users.getNumber());
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSADMIN')")
    @GetMapping("/driverList")
    public ResponseEntity<Page<UserDto>> getDrivers(@CurrentUser UserPrincipal userPrincipal, final Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<UserDto> drivers = userService.findAllByRolesContains(User.Role.DRIVER, pageable);
        LOGGER.info("Return userList.size:{}", drivers.getNumber());
        return drivers.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/drivers")
    public ResponseEntity<?> getDrivers() {
        LOGGER.info("REST request. Path:/user method: GET.");
        final List<UserDto> drivers = userService.findAllByRolesContains(User.Role.DRIVER);
        return drivers.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSADMIN') or hasAuthority('ADMIN')")
    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody UserDto user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        if (userService.existsByEmail(user.getEmail()) || userService.existsByLogin(user.getLogin())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error. This email already exists!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final Integer clientCompanyId = userPrincipal.getClientCompanyId();
            if (clientCompanyId != null) {
                final ClientCompanyDto clientCompany = clientCompanyService.findById(clientCompanyId);
                user.setClientCompany(
                        Objects.nonNull(clientCompany) ?
                                clientCompany : null);
            }
            ConfirmationToken confirmationToken = new ConfirmationToken(user.transformToEntity());
            confirmationTokenService.save(confirmationToken);
            String message = String.format("Hello, %s! To confirm your account, " +
                            "please visit next link: http://localhost:3000/confirm/%s",
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
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal userPrincipal, final @Valid @RequestBody UserDto user) {
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @DeleteMapping("/{selectedUsers}")
    public ResponseEntity<?> remove(@CurrentUser UserPrincipal userPrincipal,
                                    final @PathVariable("selectedUsers") String selectedUsers) {
        LOGGER.info("REST request. Path:/user/{} method: DELETE.", selectedUsers);
        final String delimeter = ",";
        final String[] usersId = selectedUsers.split(delimeter);
        for (String id : usersId) {
            userService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @Transactional
    @GetMapping("/confirm-account/{confirmationToken}")
    public ResponseEntity<?> confirmUserAccount(final @PathVariable String confirmationToken) {
        final ConfirmationToken token =
                confirmationTokenService.findByConfirmationToken(confirmationToken);
        if (token != null) {
            final UserDto user = userService.findByEmailIgnoreCase(token.getUser().getEmail());
            if (!user.isEnabled()) {
                user.setEnabled(true);
                userService.save(user);
                LOGGER.info("Users field isEnabled was change.");
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
    public ResponseEntity<?> authenticateUser(final @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<UserDto>> getAll(final Pageable pageable, final @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/user method: GET.");
        final Page<UserDto> userDtos = userService.findAllByQuery(pageable, query);
        LOGGER.info("Return userList.size:{}", userDtos.getNumber());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/")
    public ResponseEntity<Page<UserDto>> getAllWithoutQuery(final Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        final Page<UserDto> userDtos = userService.findAll(pageable);
        LOGGER.info("Return userList.size:{}", userDtos.getNumber());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
