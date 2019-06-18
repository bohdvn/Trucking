package by.itechart.server.service;

import by.itechart.server.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void save(final RequestDto requestDto);

    Page<RequestDto> findAll(final Pageable pageable);

    Page<RequestDto> findAllByQuery(final Pageable pageable, final String query);

    RequestDto findById(final int id);

    void delete(final RequestDto request);

    void deleteById(final int id);
}
