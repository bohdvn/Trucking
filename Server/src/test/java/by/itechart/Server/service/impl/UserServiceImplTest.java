package by.itechart.Server.service.impl;

import by.itechart.Server.entity.User;
import by.itechart.Server.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService=new UserServiceImpl(userRepository);

    private User user;
    @Before
    public void setUp() {
        user=new User();
        user.setId(1);
        user.setEmail("test@mail.en");
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setBirthDate(LocalDate.now());
        user.setLogin("login");
        user.setPassword("pass");
        user.setPassportNumber("test");
        user.setPassportIssued("test");
    }

    @After
    public void tearDown() {
        user=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(Arrays.asList(user),userService.findAll());
    }

    @Test
    public void findById() {
        Optional<User> optional=Optional.of(user);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(optional,userService.findById(anyInt()));
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}