package by.itechart.Server.controller;

import by.itechart.Server.dto.AddressDto;
import by.itechart.Server.service.AddressService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody AddressDto address) {
        LOGGER.info("REST request. Path:/address method: POST. address: {}", address);
        addressService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/address/{} method: DELETE.", id);
        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAll() {
        LOGGER.info("REST request. Path:/address method: GET.");
        final List<AddressDto> addressDtos = addressService.findAll();
        LOGGER.info("Return addressList.size:{}", addressDtos.size());
        return addressDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/address/{} method: GET.", id);
        final AddressDto addressDto = addressService.findById(id);
        return addressDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(addressDto);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
