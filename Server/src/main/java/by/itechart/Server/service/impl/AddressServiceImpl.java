package by.itechart.Server.service.impl;

import by.itechart.Server.dto.AddressDto;
import by.itechart.Server.entity.Address;
import by.itechart.Server.repository.AddressRepository;
import by.itechart.Server.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return addressRepository.findById(id).isPresent() ? addressRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(final int id) {
        addressRepository.deleteById(id);
    }
}
