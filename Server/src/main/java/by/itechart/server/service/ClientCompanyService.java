package by.itechart.server.service;

import by.itechart.server.dto.ClientCompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientCompanyService {
    void save(ClientCompanyDto clientCompanyDto);

    Page<ClientCompanyDto> findAll(Pageable pageable);

    ClientCompanyDto findById(int id);

    void delete(ClientCompanyDto clientCompany);

    void deleteById(int id);

    Page<ClientCompanyDto> findAllByQuery(final Pageable pageable, final String query);
}
