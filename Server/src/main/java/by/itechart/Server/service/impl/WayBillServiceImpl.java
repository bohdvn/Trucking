package by.itechart.Server.service.impl;

import by.itechart.Server.entity.WayBill;
import by.itechart.Server.repository.WayBillRepository;
import by.itechart.Server.service.WayBillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WayBillServiceImpl implements WayBillService {
    private WayBillRepository wayBillRepository;

    public WayBillServiceImpl(WayBillRepository wayBillRepository){
        this.wayBillRepository=wayBillRepository;
    }

    @Override
    public void save(WayBill wayBill) {
        wayBillRepository.save(wayBill);
    }

    @Override
    public List<WayBill> findAll() {
        return wayBillRepository.findAll();
    }

    @Override
    public Optional<WayBill> findById(int id) {
        return wayBillRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        wayBillRepository.deleteById(id);
    }
}
