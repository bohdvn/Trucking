package by.itechart.server.service.impl;

import by.itechart.server.dto.UserDto;
import by.itechart.server.entity.User;
import by.itechart.server.repository.UserRepository;
import by.itechart.server.service.UserService;
import by.itechart.server.specifications.CustomSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(final UserDto user) {
        userRepository.save(user.transformToEntity());
    }

    @Override
    public Page<UserDto> findAllByClientCompanyId(final int id, final Pageable pageable) {
        Page<User> users = userRepository.findAllByClientCompanyId(id, pageable);
        return new PageImpl<>(users.stream().map(User::transformToDto)
                .sorted(Comparator.comparing(UserDto::getSurname))
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(User::transformToDto).collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> findAllByClientCompanyId(final int id, final Pageable pageable, final String query) {
        final Map<List<String>, Object> conditions = new HashMap<>();
        conditions.put(Arrays.asList("clientCompany", "id"), id);

        final SearchCriteria<User> newSearchCriteria = new SearchCriteria(conditions, User.class, query);
        final Specification<User> specification = new CustomSpecification<>(newSearchCriteria);
        Page<User> users = userRepository.findAll(specification, pageable);
        return new PageImpl<>(users.stream().map(User::transformToDto)
                .sorted(Comparator.comparing(UserDto::getSurname))
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public List<User> getAllByBirthday(final int month, final int day) {
        return userRepository.findByMatchMonthAndMatchDay(month, day);
    }

    @Override
    public UserDto findById(final int id) {
        return userRepository.findById(id).isPresent() ? userRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void delete(final UserDto user) {
        userRepository.delete(user.transformToEntity());
    }

    @Override
    public void deleteById(final int id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByLogin(final String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto findByEmailIgnoreCase(final String email) {
        return userRepository.findByEmailIgnoreCase(email).transformToDto();
    }

    @Override
    public Page<UserDto> findAllByRolesContains(final User.Role role, final Pageable pageable) {
        Page<User> users = userRepository.findAllByRolesContains(role, pageable);
        return new PageImpl<>(users.stream().map(User::transformToDto)
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public Page<UserDto> findAllByRolesContains(final User.Role role, final Pageable pageable, final String query) {
        final Map<List<String>, Object> conditions = new HashMap<>();
        conditions.put(Arrays.asList("roles"), role);

        final SearchCriteria<User> newSearchCriteria = new SearchCriteria(conditions, User.class, query);
        final Specification<User> specification = new CustomSpecification<>(newSearchCriteria);
        final Page<User> users = userRepository.findAll(specification, pageable);
        return new PageImpl<>(users.stream().map(User::transformToDto)
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public List<UserDto> findAllByRolesContains(User.Role role) {
        return userRepository.findAllByRolesContains(User.Role.DRIVER)
                .stream().map(User::transformToDto)
                .collect(Collectors.toList());
    }
}
