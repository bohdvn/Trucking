package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Car;
import by.itechart.Server.repository.CarRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarServiceImpl carService=new CarServiceImpl(carRepository);

    private Car car;

    @Before
    public void setUp() throws Exception {
        car=new Car();
        car.setId(1);
        car.setName("test");
        car.setConsumption(123);
        car.setStatus(Car.Status.AVAILABLE);
        car.setCarType(Car.CarType.TANKER);
    }

    @After
    public void tearDown() throws Exception {
        car=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
//        Mockito.when(carRepository.findAll()).thenReturn(Arrays.asList(car));
//        assertEquals(Arrays.asList(car),carService.findAll());
    }

    @Test
    public void findById() {
        Optional<Car> optional= Optional.of(car);
        Mockito.when(carRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(optional,carService.findById(anyInt()));
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}