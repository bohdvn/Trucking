package by.itechart.Server.service;

import by.itechart.Server.dto.AddressDto;
import by.itechart.Server.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressService {

    void save(final AddressDto addressDto);

    List<AddressDto> findAll();

    AddressDto findById(final int id);

    void deleteById(final int id);
}
