package by.itechart.Server.controller;

import by.itechart.Server.dto.InvoiceDto;
import by.itechart.Server.entity.Invoice;
import by.itechart.Server.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody InvoiceDto invoiceDto) {
        LOGGER.info("REST request. Path:/invoice method: POST. invoice: {}", invoiceDto);
        invoiceService.save(invoiceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/invoice/{} method: GET.", id);
        final InvoiceDto invoiceDto = invoiceService.findById(id);
        return invoiceDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(invoiceDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/invoice/{} method: DELETE.", id);
        invoiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDto>> getAll() {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        List<InvoiceDto> invoicesDto = invoiceService.findAll();
        LOGGER.info("Return invoiceList.size:{}", invoicesDto.size());
        return invoicesDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(invoicesDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<InvoiceDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        final Page<InvoiceDto> invoiceDtos = invoiceService.findAll(pageable);
        LOGGER.info("Return carList.size:{}", invoiceDtos.getNumber());
        return invoiceDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
    }
}
