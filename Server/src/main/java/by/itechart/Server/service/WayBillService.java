package by.itechart.Server.service;

import by.itechart.Server.entity.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WayBillService {

    void save(WayBill wayBill);

    Page<WayBill> findAll(Pageable pageable);

    Optional<WayBill> findById(int id);

    void deleteById(int id);
}
