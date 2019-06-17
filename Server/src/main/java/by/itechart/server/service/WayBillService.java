package by.itechart.server.service;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.entity.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {

    void save(final WayBillDto wayBillDto);

    Page<WayBillDto> findAllByInvoiceRequestDriverIdAndStatus(final int id, WayBill.Status status,final Pageable pageable);

    WayBillDto findByIdAndInvoiceRequestDriverId(final int id, final int driverId);

    void deleteById(final int id);
}
