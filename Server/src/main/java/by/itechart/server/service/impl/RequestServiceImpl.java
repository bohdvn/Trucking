package by.itechart.server.service.impl;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.dto.RequestDto;
import by.itechart.server.entity.Product;
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
        final Request request=requestDto.transformToEntity();
        request.getProducts().forEach(product -> product.setRequest(request));
        requestRepository.save(request);
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromId(final int id, final Pageable pageable) {
        final Page<Request> requests = requestRepository.findAllByClientCompanyFromId(id,pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .sorted(Comparator.comparing(RequestDto::getStatus))
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromIdAndStatus(final int id, Request.Status status,
                                                                  final Pageable pageable) {
        final Page<Request> requests = requestRepository.findAllByClientCompanyFromIdAndStatus(id,status,pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
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
