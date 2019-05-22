package by.itechart.Server.service.impl;

import by.itechart.Server.entity.User;
import by.itechart.Server.repository.UserRepository;
import by.itechart.Server.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}
