package by.itechart.Server.service;

import by.itechart.Server.dto.AddressDto;

import java.util.List;

public interface AddressService {

    void save(final AddressDto addressDto);

    List<AddressDto> findAll();

    AddressDto findById(final int id);

    void deleteById(final int id);
}
