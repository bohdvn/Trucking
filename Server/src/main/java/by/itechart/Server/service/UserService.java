package by.itechart.Server.service;

import by.itechart.Server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    Optional<User> findById(int id);

    void delete(User user);

    void deleteById(int id);

    User findByEmailIgnoreCase(String email);
}
