package by.itechart.Server.service;

import by.itechart.Server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RequestService {

    void save(Request request);

    Page<Request> findAll(Pageable pageable);

    Optional<Request> findById(int id);

    void delete(Request request);

    void deleteById(int id);
}
