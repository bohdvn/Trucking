package by.itechart.Server.service;

import by.itechart.Server.entity.WayBill;

import java.util.List;
import java.util.Optional;

public interface WayBillService {

    void save(WayBill wayBill);

    List<WayBill> findAll();

    Optional<WayBill> findById(int id);

    void deleteById(int id);
}
