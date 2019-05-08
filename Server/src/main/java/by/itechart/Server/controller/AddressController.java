package by.itechart.Server.controller;

import by.itechart.Server.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService=addressService;
    }
}
