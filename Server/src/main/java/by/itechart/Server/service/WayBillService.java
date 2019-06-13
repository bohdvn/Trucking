package by.itechart.Server.service;

import by.itechart.Server.dto.WayBillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {

    void save(final WayBillDto wayBillDto);

    Page<WayBillDto> findAll(final Pageable pageable);

    WayBillDto findById(final int id);

    void deleteById(final int id);
}
