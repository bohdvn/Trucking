package by.itechart.Server.service;

import by.itechart.Server.entity.Car;
import by.itechart.Server.util.CarUtil;
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
public class CarServiceTest {
    @Resource
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Resource
    private CarService carService;

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testSave() {
        carService.save(CarUtil.createCar());
    }

    @Test
    public void testFindAll() {
//        Collection<Car> cars = carService.findAll();
//        Assert.assertTrue(cars.size() > 0);
    }

    @Test
    public void testGetOne() {
        Optional<Car> car=carService.findById(1);
        Assert.assertNotNull(car);
    }
}
