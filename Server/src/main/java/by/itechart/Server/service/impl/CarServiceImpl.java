package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Car;
import by.itechart.Server.repository.CarRepository;
import by.itechart.Server.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository){
        this.carRepository=carRepository;
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getOne(int id) {
        return carRepository.getOne(id);
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Override
    public void deleteById(int id) {
        carRepository.deleteById(id);
    }
}
