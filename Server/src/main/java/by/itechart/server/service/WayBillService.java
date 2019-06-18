package by.itechart.server.service;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.entity.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {

    Page<WayBillDto> findAllByInvoiceRequestDriverIdAndStatus(final int id, WayBill.Status status,final Pageable pageable);

    WayBillDto findByIdAndInvoiceRequestDriverId(final int id, final int driverId);

    void save(final WayBillDto wayBillDto);

    Page<WayBillDto> findAll(final Pageable pageable);

    Page<WayBillDto> findAllByQuery(final Pageable pageable, final String query);

    WayBillDto findById(final int id);

    void deleteById(final int id);
}
