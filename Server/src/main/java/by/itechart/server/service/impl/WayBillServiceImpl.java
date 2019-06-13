package by.itechart.server.service.impl;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.entity.WayBill;
import by.itechart.server.repository.WayBillRepository;
import by.itechart.server.service.WayBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class WayBillServiceImpl implements WayBillService {
    private WayBillRepository wayBillRepository;

    public WayBillServiceImpl(WayBillRepository wayBillRepository) {
        this.wayBillRepository = wayBillRepository;
    }

    @Override
    public void save(final WayBillDto wayBillDto) {
        wayBillRepository.save(wayBillDto.transformToEntity());
    }

    @Override
    public Page<WayBillDto> findAll(final Pageable pageable) {
        final Page<WayBill> wayBills = wayBillRepository.findAll(pageable);
        return new PageImpl<>(wayBills.stream().map(WayBill::transformToDto)
                .sorted(Comparator.comparing(WayBillDto::getDateFrom))
                .collect(Collectors.toList()), pageable, wayBills.getTotalElements());
    }

    @Override
    public WayBillDto findById(int id) {
        return wayBillRepository.findById(id).isPresent()? wayBillRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(int id) {
        wayBillRepository.deleteById(id);
    }
}
