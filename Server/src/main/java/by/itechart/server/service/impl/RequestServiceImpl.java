package by.itechart.server.service.impl;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.dto.RequestDto;
import by.itechart.server.entity.Request;
import by.itechart.server.repository.ClientCompanyRepository;
import by.itechart.server.repository.RequestRepository;
import by.itechart.server.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;


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
    public void save(final RequestDto requestDto) {
        if (Objects.isNull(requestDto.getId())) {
            //костыыыыль
            requestDto.setClientCompanyTo(clientCompanyRepository.getOne(1).transformToDto());
            requestDto.setClientCompanyFrom(clientCompanyRepository.getOne(1).transformToDto());
            requestRepository.save(requestDto.transformToEntity());
        } else {
            final Request entity = requestRepository.findById(requestDto.getId()).orElseThrow(IllegalStateException::new);
            requestDto.getProducts().forEach(prod -> prod.setRequest(entity.transformToDto()));
            entity.setStatus(requestDto.getStatus());
            entity.setClientCompanyFrom(requestDto.getClientCompanyFrom().transformToEntity());
            entity.setClientCompanyTo(requestDto.getClientCompanyTo().transformToEntity());
            entity.setCar(requestDto.getCar().transformToEntity());
            entity.setDriver(requestDto.getDriver().transformToEntity());
            entity.setProducts(requestDto.getProducts()
                    .stream().map(ProductDto::transformToEntity).collect(Collectors.toList()));
            requestRepository.save(entity);
        }
        /*
        request.getProducts().forEach(prod -> prod.setRequest(request));
        final Request saved = requestRepository.save(request);*/
    }

    @Override
    public Page<RequestDto> findAll(final Pageable pageable) {
        final Page<Request> requests = requestRepository.findAll(pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .sorted(Comparator.comparing(RequestDto::getStatus))
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }

    @Override
    public RequestDto findById(final int id) {
        return requestRepository.findById(id).isPresent() ?
                requestRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void delete(final RequestDto requestDto) {
        requestRepository.delete(requestDto.transformToEntity());
    }

    @Override
    public void deleteById(final int id) {
        requestRepository.deleteById(id);
    }
}
