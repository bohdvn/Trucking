package by.itechart.Server.controller;

import by.itechart.Server.dto.AddressDto;
import by.itechart.Server.entity.Address;
import by.itechart.Server.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService){ this.addressService=addressService; }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Address address){
        addressService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAll() {
        List<Address> addresses = addressService.findAll();
        List<AddressDto> addressesDto = addresses.stream().map(Address :: transform).collect(Collectors.toList());
        return addresses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(addressesDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        Optional<Address> address = addressService.findById(id);
        return address.isPresent()?
                ResponseEntity.ok().body(address.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
