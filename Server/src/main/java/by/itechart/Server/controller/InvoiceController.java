package by.itechart.Server.controller;

import by.itechart.Server.dto.InvoiceDto;
import by.itechart.Server.entity.Invoice;
import by.itechart.Server.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService=invoiceService;
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Invoice invoice){
        invoiceService.save(invoice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<Invoice> invoice = invoiceService.findById(id);
        return invoice.isPresent()?
                ResponseEntity.ok().body(invoice.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        invoiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDto>> getAll() {
        List<Invoice> invoices = invoiceService.findAll();
        List<InvoiceDto> carsDto = invoices.stream().map(Invoice::transform).collect(Collectors.toList());
        return invoices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(carsDto,
                        HttpStatus.OK);
    }
}
