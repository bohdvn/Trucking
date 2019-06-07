package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Car;
import by.itechart.Server.entity.Request;
import by.itechart.Server.repository.ClientCompanyRepository;
import by.itechart.Server.repository.RequestRepository;
import by.itechart.Server.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private ClientCompanyRepository clientCompanyRepository;

    public RequestServiceImpl(RequestRepository requestRepository, ClientCompanyRepository clientCompanyRepository) {
        this.requestRepository = requestRepository;
        this.clientCompanyRepository = clientCompanyRepository;
    }

    @Override
    @Transactional
    public void save(final Request request) {
        if (Objects.isNull(request.getId())) {
            //костыыыыль
            request.setClientCompanyTo(clientCompanyRepository.getOne(1));
            request.setClientCompanyFrom(clientCompanyRepository.getOne(1));

            requestRepository.save(request);
        } else {
            final Request entity = requestRepository.findById(request.getId()).orElseThrow(IllegalStateException::new);
            request.getProducts().forEach(prod -> prod.setRequest(entity));
            entity.setStatus(request.getStatus());
            entity.setClientCompanyFrom(request.getClientCompanyFrom());
            entity.setClientCompanyTo(request.getClientCompanyTo());
            entity.setCar(request.getCar());
            entity.setDriver(request.getDriver());
            entity.setProducts(request.getProducts());
            requestRepository.save(entity);
        }
        /*
        request.getProducts().forEach(prod -> prod.setRequest(request));
        final Request saved = requestRepository.save(request);*/
    }

    @Override
    public Page<Request> findAll(final Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    @Override
    public Optional<Request> findById(final int id) {
        return requestRepository.findById(id);
    }

    @Override
    public void delete(final Request request) {
        requestRepository.delete(request);
    }

    @Override
    public void deleteById(final int id) {
        requestRepository.deleteById(id);
    }
}
