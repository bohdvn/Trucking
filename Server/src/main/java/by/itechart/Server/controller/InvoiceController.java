package by.itechart.Server.controller;

import by.itechart.Server.service.InvoiceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService=invoiceService;
    }
}
