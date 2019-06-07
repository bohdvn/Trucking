package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Invoice;
import by.itechart.Server.repository.InvoiceRepository;
import by.itechart.Server.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository=invoiceRepository;
    }


    @Override
    @Transactional
    public void save(Invoice invoice) { invoiceRepository.save(invoice); }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> findById(int id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }
}
