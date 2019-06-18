package by.itechart.server.service.impl;

import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.entity.Invoice;
import by.itechart.server.repository.InvoiceRepository;
import by.itechart.server.service.InvoiceService;
import by.itechart.server.service.RequestService;
import by.itechart.server.specifications.InvoiceSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private RequestService requestService;

    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(RequestService requestService, InvoiceRepository invoiceRepository) {
        this.requestService = requestService;
        this.invoiceRepository = invoiceRepository;
    }


    @Override
    @Transactional
    public void save(InvoiceDto invoiceDto) {
        requestService.save(invoiceDto.getRequest());
        invoiceRepository.save(invoiceDto.transformToEntity());
    }

    @Override
    public List<InvoiceDto> findAll() {
        return invoiceRepository.findAll().stream().map(Invoice::transformToDto).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto findById(int id) {
        return invoiceRepository.findById(id).isPresent() ?
                invoiceRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(int id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Page<InvoiceDto> findAll(Pageable pageable) {
        final Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return new PageImpl<>(invoices.stream().map(Invoice::transformToDto)
                .sorted(Comparator.comparing(InvoiceDto::getStatus))
                .collect(Collectors.toList()), pageable, invoices.getTotalElements());

    }

    @Override
    public Page<InvoiceDto> findAllByQuery(final Pageable pageable, final String query) {
        final InvoiceSpecification invoiceSpecification = new InvoiceSpecification(
                new SearchCriteria(query, null, -1, InvoiceDto.class));
        final Page<Invoice> invoices = invoiceRepository.findAll(invoiceSpecification, pageable);
        return new PageImpl<>(invoices.stream().map(Invoice::transformToDto)
                .sorted(Comparator.comparing(InvoiceDto::getStatus))
                .collect(Collectors.toList()), pageable, invoices.getTotalElements());
    }
}
