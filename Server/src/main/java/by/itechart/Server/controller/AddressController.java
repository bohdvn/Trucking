package by.itechart.Server.controller;

import by.itechart.Server.dto.AddressDto;
import by.itechart.Server.entity.Address;
import by.itechart.Server.service.AddressService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService){ this.addressService=addressService; }

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @PutMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Address address){
        LOGGER.info("REST request. Path:/address method: POST. address: {}", address);
        addressService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/address/{} method: DELETE.", id);
        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAll() {
        LOGGER.info("REST request. Path:/address method: GET.");
        List<Address> addresses = addressService.findAll();
        List<AddressDto> addressesDto = addresses.stream().map(Address :: transform).collect(Collectors.toList());
        LOGGER.info("Return addressList.size:{}", addressesDto.size());
        return addresses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(addressesDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/address/{} method: GET.", id);
        Optional<Address> address = addressService.findById(id);
        return address.isPresent()?
                ResponseEntity.ok().body(address.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(final MethodArgumentNotValidException ex){
//        return ValidationUtils.getErrorMap(ex);
//    }
}
