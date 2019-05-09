package by.itechart.Server.service;

import by.itechart.Server.entity.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> findById(int id);
}
