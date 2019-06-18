package by.itechart.server.service;

import by.itechart.server.dto.UserDto;
import by.itechart.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserDto> findAllByClientCompanyId(final int id, final Pageable pageable);

    Page<UserDto> findAllByClientCompanyId(final int id, final Pageable pageable, final String query);

    Page<UserDto> findAllByRolesContains(final User.Role role, final Pageable pageable);

    Page<UserDto> findAllByRolesContains(final User.Role role, final Pageable pageable, final String query);

    List<UserDto> findAllByRolesContains(final User.Role role);

    Page<UserDto> findAll(final Pageable pageable);

    void save(final UserDto userDto);

    UserDto findById(int id);

    void delete(final UserDto userDto);

    void deleteById(final int id);

    List<UserDto> findAll();

    boolean existsByLogin(final String login);

    boolean existsByEmail(final String email);

    UserDto findByEmailIgnoreCase(final String email);

    List<User> getAllByBirthday(final int month, final int day);
}
