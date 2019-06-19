package by.itechart.server.service;

import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.entity.Invoice;
import by.itechart.server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {

    void save(InvoiceDto invoiceDto);

    InvoiceDto findById(int id);

    void deleteById(int id);

    Page<InvoiceDto> findAllByRequestClientCompanyFromIdAndStatus(final int id, Invoice.Status status, Pageable pageable);
}

