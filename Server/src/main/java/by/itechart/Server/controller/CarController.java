package by.itechart.Server.controller;

import by.itechart.Server.dto.CarDto;
import by.itechart.Server.entity.Car;
import by.itechart.Server.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @PutMapping("/")
    public ResponseEntity<?> edit(final @RequestBody Car car) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", car);
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<?> create(final @RequestBody Car car) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", car);
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/car/{} method: GET.", id);
        Optional<Car> car = carService.findById(id);
        return car.isPresent() ?
                ResponseEntity.ok().body(car.get().transform()) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/car/{} method: DELETE.", id);
        carService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<CarDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/car method: GET.");
        Page<Car> cars = carService.findAll(pageable);
        Page<CarDto> carsDto = new PageImpl<>(cars.stream().map(Car::transform).collect(Collectors.toList()));
        LOGGER.info("Return carList.size:{}", carsDto.getNumber());
        return cars.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(carsDto,
                        HttpStatus.OK);
    }
}
