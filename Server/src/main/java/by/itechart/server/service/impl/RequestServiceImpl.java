package by.itechart.server.service.impl;

import by.itechart.server.dto.RequestDto;
import by.itechart.server.entity.Request;
import by.itechart.server.repository.RequestRepository;
import by.itechart.server.service.RequestService;
import by.itechart.server.specifications.CustomSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public void save(final RequestDto requestDto) {
        final Request request = requestDto.transformToEntity();
        request.getProducts().forEach(product -> product.setRequest(request));
        requestRepository.save(request);
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromId(final int id, final Pageable pageable) {
        final Page<Request> requests = requestRepository.findAllByClientCompanyFromId(id, pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .sorted(Comparator.comparing(RequestDto::getStatus))
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromId(final int id, final Pageable pageable,
                                                         final String query) {
        final Map<List<String>, Object> conditions = new HashMap<>();
        conditions.put(Arrays.asList("clientCompanyFrom", "id"), id);

        final SearchCriteria<Request> newSearchCriteria = new SearchCriteria(conditions, Request.class, query);
        final Specification<Request> specification = new CustomSpecification<>(newSearchCriteria);
        final Page<Request> requests = requestRepository.findAll(specification, pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }


    @Override
    public RequestDto findById(final int id) {
        return null;
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromIdAndStatus(final int id, Request.Status status,
                                                                  final Pageable pageable) {
        final Page<Request> requests = requestRepository.findAllByClientCompanyFromIdAndStatus(id, status, pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }

    @Override
    public Page<RequestDto> findAllByClientCompanyFromIdAndStatus(final int id, final Request.Status status,
                                                                  final Pageable pageable, final String query) {
        final Map<List<String>, Object> conditions = new HashMap<>();
        conditions.put(Arrays.asList("clientCompanyFrom", "id"), id);
        conditions.put(Arrays.asList("status"), status);

        final SearchCriteria<Request> newSearchCriteria = new SearchCriteria(conditions, Request.class, query);
        final Specification<Request> specification = new CustomSpecification<>(newSearchCriteria);
        final Page<Request> requests = requestRepository.findAll(specification, pageable);
        return new PageImpl<>(requests.stream().map(Request::transformToDto)
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
    }

    @Override
    public RequestDto findByIdAndClientCompanyFromId(final int id, final int clientId) {
        Optional<Request> optional = requestRepository.findByIdAndClientCompanyFromId(id, clientId);
        return optional.isPresent() ?
                optional.get().transformToDto() : null;
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
