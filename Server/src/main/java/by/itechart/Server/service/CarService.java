package by.itechart.Server.service;

import by.itechart.Server.entity.Car;

import java.util.List;

public interface CarService {
    void save(Car car);

    List<Car> findAll();

    Car getOne(int id);

    void delete(Car car);

    void deleteById(int id);
}
