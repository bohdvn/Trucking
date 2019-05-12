package by.itechart.Server.controller;

import by.itechart.Server.dto.CarDto;
import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.User;
import by.itechart.Server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/user{} method: GET.", id);
        Optional<User> user = userService.findById(id);
        return user.isPresent()?
                ResponseEntity.ok().body(user.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPage(Pageable pageable) {
        LOGGER.info("REST request. Path:/user method: GET.");
        Page<User> users = userService.findAll(pageable);
        Page<UserDto> usersDto = new PageImpl<>(users.stream().map(User::transform).collect(Collectors.toList()));
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(usersDto,
                        HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody User user){
        LOGGER.info("REST request. Path:/user method: POST. user: {}", user);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
