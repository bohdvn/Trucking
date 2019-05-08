package by.itechart.Server.service.impl;

import by.itechart.Server.repository.AddressRepository;
import by.itechart.Server.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository=addressRepository;
    }
}
