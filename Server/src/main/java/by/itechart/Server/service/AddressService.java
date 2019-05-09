package by.itechart.Server.service;

import by.itechart.Server.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressService {

    void save(Address address);

    List<Address> findAll();

    Optional<Address> findById(int id);

    void deleteById(int id);
}
