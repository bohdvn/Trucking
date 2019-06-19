package by.itechart.server.service.impl;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.entity.WayBill;
import by.itechart.server.repository.WayBillRepository;
import by.itechart.server.service.InvoiceService;
import by.itechart.server.service.WayBillService;
import by.itechart.server.specifications.CustomSpecification;
import by.itechart.server.specifications.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WayBillServiceImpl implements WayBillService {

    private InvoiceService invoiceService;


    private WayBillRepository wayBillRepository;

    public WayBillServiceImpl(InvoiceService invoiceService, WayBillRepository wayBillRepository) {
        this.invoiceService = invoiceService;
        this.wayBillRepository = wayBillRepository;
    }

    @Override
    @Transactional
    public void save(final WayBillDto wayBillDto) {
        WayBill wayBill = wayBillDto.transformToEntity();
        wayBill.getCheckpoints().forEach(checkpoint -> checkpoint.setWayBill(wayBill));
        wayBillRepository.save(wayBill);
        invoiceService.save(wayBillDto.getInvoice());
    }

    @Override
    public Page<WayBillDto> findAllByInvoiceRequestDriverIdAndStatus(final int id, WayBill.Status status, final Pageable pageable) {
        final Page<WayBill> wayBills =
                wayBillRepository.findAllByInvoiceRequestDriverIdAndStatus(id, status, pageable);
        return new PageImpl<>(wayBills.stream().map(WayBill::transformToDto)
                .sorted(Comparator.comparing(WayBillDto::getDateFrom))
                .collect(Collectors.toList()), pageable, wayBills.getTotalElements());
    }

    @Override
    public Page<WayBillDto> findAllByInvoiceRequestDriverIdAndStatus(final int id, final WayBill.Status status, final Pageable pageable, final String query) {
        final Map<List<String>, Object> conditions = new HashMap<>();
        conditions.put(Arrays.asList("invoice", "request", "id"), id);
        conditions.put(Arrays.asList("status"), status);

        final SearchCriteria<WayBill> newSearchCriteria = new SearchCriteria(conditions, WayBill.class, query);
        final Specification<WayBill> specification = new CustomSpecification<>(newSearchCriteria);
        final Page<WayBill> wayBills = wayBillRepository.findAll(specification, pageable);
        return new PageImpl<>(wayBills.stream().map(WayBill::transformToDto)
                .sorted(Comparator.comparing(WayBillDto::getDateFrom))
                .collect(Collectors.toList()), pageable, wayBills.getTotalElements());
    }

    @Override
    public WayBillDto findById(int id) {
        return wayBillRepository.findById(id).isPresent() ? wayBillRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public WayBillDto findByIdAndInvoiceRequestDriverId(final int id, final int driverId) {
        Optional<WayBill> optionalWayBill = wayBillRepository.findByIdAndInvoiceRequestDriverId(id, driverId);
        return optionalWayBill.isPresent() ? optionalWayBill.get().transformToDto() : null;
    }

    @Override
    public void deleteById(int id) {
        wayBillRepository.deleteById(id);
    }
}
