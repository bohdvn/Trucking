package by.itechart.Server.service.impl;

import by.itechart.Server.repository.InvoiceRepository;
import by.itechart.Server.service.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository=invoiceRepository;
    }
}
