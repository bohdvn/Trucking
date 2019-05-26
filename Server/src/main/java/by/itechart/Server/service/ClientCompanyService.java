package by.itechart.Server.service;

import by.itechart.Server.entity.ClientCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientCompanyService {
    void save(ClientCompany clientCompany);

    Page<ClientCompany> findAll(Pageable pageable);

    Optional<ClientCompany> findById(int id);

    void delete(ClientCompany clientCompany);

    void deleteById(int id);
}
