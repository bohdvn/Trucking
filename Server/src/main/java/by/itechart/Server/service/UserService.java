package by.itechart.Server.service;

import by.itechart.Server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    Page<User> findAllByClientCompanyId(int id,Pageable pageable);

    Page<User> findAllByRolesContains(User.Role role,Pageable pageable);

    List<User> findAllByRolesContains(User.Role role);

    Optional<User> findById(int id);

    void delete(User user);

    void deleteById(int id);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String email);
}
