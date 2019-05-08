package by.itechart.Server.service;

import by.itechart.Server.entity.ClientCompany;

import java.util.List;

public interface ClientCompanyService {
    void save(ClientCompany clientCompany);

    List<ClientCompany> findAll();

    ClientCompany getOne(int id);

    void delete(ClientCompany clientCompany);

    void deleteById(int id);
}
