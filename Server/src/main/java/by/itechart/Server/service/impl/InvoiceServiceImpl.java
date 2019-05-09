package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Invoice;
import by.itechart.Server.repository.InvoiceRepository;
import by.itechart.Server.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository=invoiceRepository;
    }

    @Override
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
}
