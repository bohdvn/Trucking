package by.itechart.Server.service.impl;

import by.itechart.Server.dto.CarDto;
import by.itechart.Server.entity.Car;
import by.itechart.Server.repository.CarRepository;
import by.itechart.Server.service.CarService;
import by.itechart.Server.specifications.CarSpecificationBuilder;
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
//        final List<Car> cars = carRepository.findAll();
//        final List<CarDto> carDtos = new ArrayList<>();
//        for (Car car : cars) {
//            carDtos.add(car.transformToDto());
//        }
//        return carDtos;
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
        final CarSpecificationBuilder builder = new CarSpecificationBuilder();
        final String[] strings = query.split("\\s+");
        for (final String string : strings) {
            builder.with("name", string);
        }
        final Specification<Car> spec = builder.build();
        final Page<Car> cars = carRepository.findAll(spec, pageable);
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
