package by.itechart.Server.controller;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.User;
import by.itechart.Server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<User> user = userService.findById(id);
        return user.isPresent()?
                ResponseEntity.ok().body(user.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody User user){
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
