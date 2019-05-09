package by.itechart.Server.service;

import by.itechart.Server.entity.Car;
import by.itechart.Server.entity.User;

import java.util.List;
import java.util.Optional;

public interface CarService {
    void save(Car car);

    List<Car> findAll();

    Optional<Car> findById(int id);

    void delete(Car car);

    void deleteById(int id);
}
