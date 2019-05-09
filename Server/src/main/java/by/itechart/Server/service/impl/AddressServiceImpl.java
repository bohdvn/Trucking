package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Address;
import by.itechart.Server.repository.AddressRepository;
import by.itechart.Server.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository=addressRepository;
    }

    @Override
    public void save(Address address) { addressRepository.save(address); }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(int id) {
        return addressRepository.findById(id);
    }

    @Override
    public void deleteById(int id) { addressRepository.deleteById(id);}
}
