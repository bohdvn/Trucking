package by.itechart.Server.service.impl;

import by.itechart.Server.repository.WayBillRepository;
import by.itechart.Server.service.WayBillService;
import org.springframework.stereotype.Service;

@Service
public class WayBillServiceImpl implements WayBillService {
    private WayBillRepository wayBillRepository;

    public WayBillServiceImpl(WayBillRepository wayBillRepository){
        this.wayBillRepository=wayBillRepository;
    }
}
