package by.itechart.Server.service;

import by.itechart.Server.dto.RequestDto;
import by.itechart.Server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RequestService {

    void save(final RequestDto requestDto);

    Page<RequestDto> findAll(final Pageable pageable);

    RequestDto findById(final int id);

    void delete(final RequestDto request);

    void deleteById(final int id);
}
