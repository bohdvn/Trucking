package by.itechart.Server.service;

import by.itechart.Server.dto.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {
    void save(final CarDto carDto);

    List<CarDto> findAll();

    Page<CarDto> findAll(final Pageable pageable);

    CarDto findById(final int id);

    void delete(final CarDto carDto);

    void deleteById(final int id);

    Page<CarDto> findAllByQuery(final Pageable pageable, final String query);
}
