package by.itechart.server.service.impl;

import by.itechart.server.dto.CarDto;
import by.itechart.server.entity.Car;
import by.itechart.server.repository.CarRepository;
import by.itechart.server.service.CarService;
import by.itechart.server.specifications.CustomSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void save(final CarDto carDto) {
        carRepository.save(carDto.transformToEntity());
    }

    @Override
    public List<CarDto> findAll() {
        return carRepository.findAll().stream().map(Car::transformToDto).collect(Collectors.toList());
    }

    @Override
    public Page<CarDto> findAll(final Pageable pageable) {
        final Page<Car> cars = carRepository.findAll(pageable);
        return new PageImpl<>(cars.stream().map(Car::transformToDto)
                .sorted(Comparator.comparing(CarDto::getStatus))
                .collect(Collectors.toList()), pageable, cars.getTotalElements());
    }

    @Override
    public Page<CarDto> findAllByQuery(final Pageable pageable, final String query) {
        Specification<Car> specification = new CustomSpecification<>(new SearchCriteria(query, CarDto.class));
        final Page<Car> cars = carRepository.findAll(specification, pageable);
        return new PageImpl<>(cars.stream().map(Car::transformToDto)
                .sorted(Comparator.comparing(CarDto::getStatus))
                .collect(Collectors.toList()), pageable, cars.getTotalElements());
    }

    @Override
    public CarDto findById(final int id) {
        return carRepository.findById(id).isPresent() ? carRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void delete(final CarDto carDto) {
        carRepository.delete(carDto.transformToEntity());
    }

    @Override
    public void deleteById(final int id) {
        carRepository.deleteById(id);
    }
}
