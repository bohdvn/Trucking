package by.itechart.Server.service.impl;

import by.itechart.Server.dto.ClientCompanyDto;
import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.repository.ClientCompanyRepository;
import by.itechart.Server.service.ClientCompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ClientCompanyServiceImpl implements ClientCompanyService {
    private ClientCompanyRepository clientCompanyRepository;

    public ClientCompanyServiceImpl(ClientCompanyRepository clientCompanyRepository) {
        this.clientCompanyRepository = clientCompanyRepository;
    }

    @Override
    public void save(final ClientCompanyDto clientCompanyDto) {
        clientCompanyRepository.save(clientCompanyDto.transformToEntity());
    }

    @Override
    public Page<ClientCompanyDto> findAll(Pageable pageable) {
        final Page<ClientCompany> companies = clientCompanyRepository.findAll(pageable);
        return new PageImpl<>(companies.stream().map(ClientCompany::transformToDto)
                .sorted(Comparator.comparing(ClientCompanyDto::getName))
                .collect(Collectors.toList()), pageable, companies.getTotalElements());
    }

    @Override
    public ClientCompanyDto findById(int id) {
        return clientCompanyRepository.findById(id).isPresent() ?
                clientCompanyRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void delete(ClientCompanyDto clientCompanyDto) {
        clientCompanyRepository.delete(clientCompanyDto.transformToEntity());
    }

    @Override
    public void deleteById(int id) {
        clientCompanyRepository.deleteById(id);
    }
}
