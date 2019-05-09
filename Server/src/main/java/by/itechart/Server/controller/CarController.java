package by.itechart.Server.controller;

import by.itechart.Server.dto.CarDto;
import by.itechart.Server.entity.Car;
import by.itechart.Server.entity.User;
import by.itechart.Server.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {
    private CarService carService;

    public CarController(CarService carService){
        this.carService=carService;
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Car car){
        carService.save(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<Car> car = carService.findById(id);
        return car.isPresent()?
                ResponseEntity.ok().body(car.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
            carService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> getAll() {
        List<Car> cars = carService.findAll();
        List<CarDto> carsDto = cars.stream().map(Car::transform).collect(Collectors.toList());
        return cars.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
               new ResponseEntity<>(carsDto,
                       HttpStatus.OK);
    }
}
