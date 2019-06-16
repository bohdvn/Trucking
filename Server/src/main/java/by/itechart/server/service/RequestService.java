package by.itechart.server.service;

import by.itechart.server.dto.RequestDto;
import by.itechart.server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void save(final RequestDto requestDto);

    Page<RequestDto> findAllByClientCompanyFromId(final int id, final Pageable pageable);

    Page<RequestDto> findAllByClientCompanyFromIdAndStatus(final int id, Request.Status status, final Pageable pageable);

    RequestDto findById(final int id);

    void delete(final RequestDto request);

    void deleteById(final int id);
}
