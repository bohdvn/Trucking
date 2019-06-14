package by.itechart.server.service;

import by.itechart.server.dto.AddressDto;

import java.util.List;

public interface AddressService {

    void save(final AddressDto addressDto);

    List<AddressDto> findAll();

    AddressDto findById(final int id);

    void deleteById(final int id);
}
