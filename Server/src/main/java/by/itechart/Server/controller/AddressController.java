package by.itechart.Server.controller;

import by.itechart.Server.entity.Address;
import by.itechart.Server.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService=addressService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        Optional<Address> address = addressService.findById(id);
        return address.isPresent()?
                ResponseEntity.ok().body(address.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
