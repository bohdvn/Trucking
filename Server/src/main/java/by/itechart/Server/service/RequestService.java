package by.itechart.Server.service;

import by.itechart.Server.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void save(final RequestDto requestDto);

    Page<RequestDto> findAll(final Pageable pageable);

    RequestDto findById(final int id);

    void delete(final RequestDto request);

    void deleteById(final int id);
}
