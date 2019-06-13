package by.itechart.Server.service;

import by.itechart.Server.dto.InvoiceDto;
import by.itechart.Server.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    void save(InvoiceDto invoiceDto);

    List<InvoiceDto> findAll();

    InvoiceDto findById(int id);

    void deleteById(int id);

    Page<InvoiceDto> findAll(Pageable pageable);
}
