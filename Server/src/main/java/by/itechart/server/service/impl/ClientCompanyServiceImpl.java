package by.itechart.server.service.impl;

import by.itechart.server.dto.ClientCompanyDto;
import by.itechart.server.entity.ClientCompany;
import by.itechart.server.entity.User;
import by.itechart.server.repository.ClientCompanyRepository;
import by.itechart.server.service.ClientCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ClientCompanyServiceImpl implements ClientCompanyService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ClientCompanyRepository clientCompanyRepository;

    public ClientCompanyServiceImpl(ClientCompanyRepository clientCompanyRepository) {
        this.clientCompanyRepository = clientCompanyRepository;
    }

    @Override
    public void save(final ClientCompanyDto clientCompanyDto) {
        final ClientCompany clientCompany = clientCompanyDto.transformToEntity();
        if (clientCompany.getUsers() != null) {
            User user = clientCompany.getUsers().get(0);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setClientCompany(clientCompany);
        }
        clientCompanyRepository.save(clientCompany);
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