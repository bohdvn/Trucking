package by.itechart.server.controller;

import by.itechart.server.dto.CarDto;
import by.itechart.server.service.CarService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> edit(final @Valid @RequestBody CarDto carDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", carDto);
        carService.save(carDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(final @Valid @RequestBody CarDto carDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", carDto);
        carService.save(carDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/car/{} method: GET.", id);
        final CarDto carDto = carService.findById(id);
        return carDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(carDto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @DeleteMapping("/{selectedCars}")
    public ResponseEntity<?> remove(@PathVariable("selectedCars") String selectedCars) {
        LOGGER.info("REST request. Path:/car/{} method: DELETE.", selectedCars);
        final String delimeter = ",";
        final String[] carsId = selectedCars.split(delimeter);
        for (String id : carsId) {
            carService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        final List<CarDto> carDtos = carService.findAll();
        return
                //carDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<CarDto>> getAll(Pageable pageable, @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/car method: GET.");
        final Page<CarDto> carsDto = carService.findAllByQuery(pageable, query);
        LOGGER.info("Return carList.size:{}", carsDto.getNumber());
        return
                //carsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(carsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/")
    public ResponseEntity<Page<CarDto>> getAllWithoutQuery(Pageable pageable) {
        LOGGER.info("REST request. Path:/car method: GET.");
        final Page<CarDto> carsDto = carService.findAll(pageable);
        LOGGER.info("Return carList.size:{}", carsDto.getNumber());
        return
                //carsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(carsDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
