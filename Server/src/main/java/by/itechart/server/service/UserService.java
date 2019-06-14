package by.itechart.server.service;

import by.itechart.server.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    Page<UserDto> findAllByQuery(final Pageable pageable, final String query);
}
