package by.itechart.server.service.impl;

import by.itechart.server.dto.AddressDto;
import by.itechart.server.entity.Address;
import by.itechart.server.repository.AddressRepository;
import by.itechart.server.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void save(final AddressDto addressDto) {
        addressRepository.save(addressDto.transformToEntity());
    }

    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream().map(Address::transformToDto).collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(int id) {
        return addressRepository.findById(id).isPresent()
                ? addressRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(final int id) {
        addressRepository.deleteById(id);
    }
}
