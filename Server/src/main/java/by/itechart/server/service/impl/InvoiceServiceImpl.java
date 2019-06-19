package by.itechart.server.service.impl;

import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.entity.Invoice;
import by.itechart.server.repository.InvoiceRepository;
import by.itechart.server.service.InvoiceService;
import by.itechart.server.service.RequestService;
import by.itechart.server.specifications.CustomSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    private RequestService requestService;

    public InvoiceServiceImpl(final InvoiceRepository invoiceRepository, final RequestService requestService) {
        this.invoiceRepository = invoiceRepository;
        this.requestService = requestService;
    }


    @Override
    @Transactional
    public void save(final InvoiceDto invoiceDto) {
        requestService.save(invoiceDto.getRequest());
        invoiceRepository.save(invoiceDto.transformToEntity());
    }

    @Override
    public List<InvoiceDto> findAll() {
        return invoiceRepository.findAll().stream().map(Invoice::transformToDto).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto findById(final int id) {
        return invoiceRepository.findById(id).isPresent() ?
                invoiceRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(final int id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Page<InvoiceDto> findAllByRequestClientCompanyFromIdAndStatus(
            final int id, Invoice.Status status, Pageable pageable) {
        final Page<Invoice> invoices = invoiceRepository
                .findAllByRequestClientCompanyFromIdAndStatus(id, status, pageable);
        return new PageImpl<>(invoices.stream().map(Invoice::transformToDto)
                .sorted(Comparator.comparing(InvoiceDto::getStatus))
                .collect(Collectors.toList()), pageable, invoices.getTotalElements());

    }

    @Override
    public Page<InvoiceDto> findAllByQuery(final Pageable pageable, final String query) {
        final SearchCriteria<Invoice> newSearchCriteria = new SearchCriteria(Invoice.class, query);
        final Specification<Invoice> specification = new CustomSpecification<>(newSearchCriteria);
        final Page<Invoice> invoices = invoiceRepository.findAll(specification, pageable);
        return new PageImpl<>(invoices.stream().map(Invoice::transformToDto)
                .sorted(Comparator.comparing(InvoiceDto::getStatus))
                .collect(Collectors.toList()), pageable, invoices.getTotalElements());
    }
}
