package by.itechart.Server.service.impl;

import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.repository.ClientCompanyRepository;
import by.itechart.Server.service.ClientCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientCompanyServiceImpl implements ClientCompanyService {
    private ClientCompanyRepository clientCompanyRepository;

    public ClientCompanyServiceImpl(ClientCompanyRepository clientCompanyRepository){
        this.clientCompanyRepository=clientCompanyRepository;
    }

    @Override
    public void save(ClientCompany clientCompany) {
        clientCompanyRepository.save(clientCompany);
    }

    @Override
    public List<ClientCompany> findAll() {
        return clientCompanyRepository.findAll();
    }

    @Override
    public Optional<ClientCompany> findById(int id) { return clientCompanyRepository.findById(id); }

    @Override
    public void delete(ClientCompany clientCompany) {
        clientCompanyRepository.delete(clientCompany);
    }

    @Override
    public void deleteById(int id) {
        clientCompanyRepository.deleteById(id);
    }
}
