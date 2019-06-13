package by.itechart.Server.service.impl;

import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.entity.User;
import by.itechart.Server.repository.ClientCompanyRepository;
import by.itechart.Server.service.ClientCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientCompanyServiceImpl implements ClientCompanyService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ClientCompanyRepository clientCompanyRepository;

    public ClientCompanyServiceImpl(ClientCompanyRepository clientCompanyRepository) {
        this.clientCompanyRepository = clientCompanyRepository;
    }

    @Override
    public void save(final ClientCompany clientCompany) {
        if(clientCompany.getUsers()!=null){
            User user=clientCompany.getUsers().get(0);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setClientCompany(clientCompany);
        }
        clientCompanyRepository.save(clientCompany);
    }

    @Override
    public Page<ClientCompany> findByCompanyType(ClientCompany.CompanyType companyType, Pageable pageable) {
        return clientCompanyRepository.findClientCompaniesByCompanyType(companyType, pageable);
    }

    @Override
    public Page<ClientCompany> findAll(Pageable pageable) {
        return clientCompanyRepository.findAll(pageable);
    }

    @Override
    public Optional<ClientCompany> findById(int id) {
        return clientCompanyRepository.findById(id);
    }

    @Override
    public void delete(ClientCompany clientCompany) {
        clientCompanyRepository.delete(clientCompany);
    }

    @Override
    public void deleteById(int id) {
        clientCompanyRepository.deleteById(id);
    }
}
