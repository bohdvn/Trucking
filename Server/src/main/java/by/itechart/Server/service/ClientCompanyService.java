package by.itechart.Server.service;

import by.itechart.Server.entity.ClientCompany;

import java.util.List;
import java.util.Optional;

public interface ClientCompanyService {
    void save(ClientCompany clientCompany);

    List<ClientCompany> findAll();

    Optional<ClientCompany> findById(int id);

    void delete(ClientCompany clientCompany);

    void deleteById(int id);
}
