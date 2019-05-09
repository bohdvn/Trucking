package by.itechart.Server.service;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    void delete(User user);

    void deleteById(int id);
}
