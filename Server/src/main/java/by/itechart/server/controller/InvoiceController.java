package by.itechart.server.controller;

import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.dto.UserDto;
import by.itechart.server.security.CurrentUser;
import by.itechart.server.security.UserPrincipal;
import by.itechart.server.service.InvoiceService;
import by.itechart.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    private InvoiceService invoiceService;

    private UserService userService;

    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService=userService;
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal user, @RequestBody InvoiceDto invoiceDto) {
        LOGGER.info("REST request. Path:/invoice method: POST. invoice: {}", invoiceDto);
        UserDto dispatcher=userService.findById(user.getId());
        invoiceDto.setDispatcherFrom(dispatcher);
        invoiceService.save(invoiceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@RequestBody InvoiceDto invoiceDto) {
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
