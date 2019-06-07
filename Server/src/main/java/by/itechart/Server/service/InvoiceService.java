package by.itechart.Server.service;

import by.itechart.Server.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    void save(Invoice invoice);

    List<Invoice> findAll();

    Optional<Invoice> findById(int id);

    void deleteById(int id);

    Page<Invoice> findAll(Pageable pageable);
}
