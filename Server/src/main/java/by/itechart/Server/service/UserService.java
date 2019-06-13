package by.itechart.Server.service;

import by.itechart.Server.dto.UserDto;
import by.itechart.Server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserDto> findAllByClientCompanyId(int id,Pageable pageable);

    Page<UserDto> findAllByRolesContains(User.Role role,Pageable pageable);

    List<UserDto> findAllByRolesContains(User.Role role);

    void save(final UserDto userDto);

    UserDto findById(int id);

    void delete(final UserDto userDto);

    void deleteById(final int id);

    boolean existsByLogin(final String login);

    boolean existsByEmail(final String email);

    UserDto findByEmailIgnoreCase(final String email);
}
