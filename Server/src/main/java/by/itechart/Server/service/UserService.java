package by.itechart.Server.service;

import by.itechart.Server.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();

    User getOne(int id);

    void delete(User user);

    void deleteById(int id);
}
