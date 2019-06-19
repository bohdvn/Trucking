package by.itechart.server.service;

import by.itechart.server.dto.InvoiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {

    void save(final InvoiceDto invoiceDto);

    List<InvoiceDto> findAll();

    InvoiceDto findById(final int id);

    void deleteById(final int id);

    Page<InvoiceDto> findAll(final Pageable pageable);

    Page<InvoiceDto> findAllByQuery(final Pageable pageable, final String query);
}

