package by.itechart.Server.controller;

import by.itechart.Server.dto.CarDto;
import by.itechart.Server.dto.InvoiceDto;
import by.itechart.Server.entity.Car;
import by.itechart.Server.entity.Invoice;
import by.itechart.Server.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Invoice invoice){
        LOGGER.info("REST request. Path:/invoice method: POST. invoice: {}", invoice);
        invoiceService.save(invoice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/invoice/{} method: GET.", id);
        Optional<Invoice> invoice = invoiceService.findById(id);
        return invoice.isPresent()?
                ResponseEntity.ok().body(invoice.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/invoice/{} method: DELETE.", id);
        invoiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDto>> getAll() {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        List<Invoice> invoices = invoiceService.findAll();
        List<InvoiceDto> invoicesDto = invoices.stream().map(Invoice::transform).collect(Collectors.toList());
        LOGGER.info("Return invoiceList.size:{}", invoicesDto.size());
        return invoices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(invoicesDto,
                        HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<InvoiceDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        final Page<Invoice> invoices = invoiceService.findAll(pageable);
        Page<InvoiceDto> invoiceDtos = new PageImpl<>(invoices.stream().map(Invoice::transform)
                .sorted(Comparator.comparing(InvoiceDto :: getStatus))
                .collect(Collectors.toList()), pageable, invoices.getTotalElements());
        LOGGER.info("Return carList.size:{}", invoiceDtos.getNumber());
        return invoices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
    }
}
