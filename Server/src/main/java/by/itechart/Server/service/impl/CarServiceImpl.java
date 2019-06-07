package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Car;
import by.itechart.Server.repository.CarRepository;
import by.itechart.Server.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository){
        this.carRepository=carRepository;
    }

    @Override
    public void save(final Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Optional<Car> findById(int id) { return carRepository.findById(id); }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Override
    public void deleteById(int id) {
        carRepository.deleteById(id);
    }
}
