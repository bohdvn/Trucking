package by.itechart.server.service;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.entity.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {

    Page<WayBillDto> findAllByInvoiceRequestDriverId(final int id, final Pageable pageable);

    Page<WayBillDto> findAllByInvoiceRequestDriverId(final int id, final Pageable pageable, final String query);

    Page<WayBillDto> findAllByInvoiceRequestClientCompanyFromId(final int id, final Pageable pageable);

    Page<WayBillDto> findAllByInvoiceRequestClientCompanyFromId(final int id,
                                                                final Pageable pageable, final String query);

    WayBillDto findByIdAndInvoiceRequestDriverId(final int id, final Integer driverId);

    WayBillDto findByIdAndInvoiceRequestClientCompanyFromId(final int id, final Integer clientId);

    void save(final WayBillDto wayBillDto);

    WayBillDto findById(final int id);

    void deleteById(final int id);
}
