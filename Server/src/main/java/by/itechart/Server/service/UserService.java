package by.itechart.Server.service;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(final UserDto userDto);

    Page<UserDto> findAll(final Pageable pageable);

    List<UserDto> findAll();

    UserDto findById(int id);

    void delete(final UserDto userDto);

    void deleteById(final int id);

    boolean existsByLogin(final String login);

    boolean existsByEmail(final String email);

    UserDto findByEmailIgnoreCase(final String email);
}
