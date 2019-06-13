package by.itechart.Server.service;

import by.itechart.Server.entity.User;
import by.itechart.Server.util.UserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Optional;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class UserServiceTest {
    @Resource
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Resource
    private UserService userService;

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

//    @Test
//    public void testSave() {
//        userService.save(UserUtil.createUser());
//    }

    @Test
    public void testFindAll() {
//        Collection<User> users = userService.findAll();
//        Assert.assertTrue(users.size() > 0);
    }

//    @Test
//    public void testGetOne() {
//        Optional<User> user=userService.findById(1);
//        Assert.assertNotNull(user);
//    }
}
