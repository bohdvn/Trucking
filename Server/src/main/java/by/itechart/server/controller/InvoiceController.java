package by.itechart.server.controller;

import by.itechart.server.dto.InvoiceDto;
import by.itechart.server.dto.UserDto;
import by.itechart.server.entity.Invoice;
import by.itechart.server.entity.Request;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    private InvoiceService invoiceService;

    private UserService userService;

    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal user, @RequestBody InvoiceDto invoiceDto) {
        LOGGER.info("REST request. Path:/invoice method: POST. invoice: {}", invoiceDto);
        UserDto dispatcher = userService.findById(user.getId());
        invoiceDto.setDispatcherFrom(dispatcher);
        invoiceService.save(invoiceDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DISPATCHER') or hasAuthority('MANAGER')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal user, @RequestBody InvoiceDto invoiceDto) {
        LOGGER.info("REST request. Path:/invoice method: POST. invoice: {}", invoiceDto);
        if (invoiceDto.getManager() == null) {
            UserDto manager = userService.findById(user.getId());
            invoiceDto.setManager(manager);
        }
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


    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<InvoiceDto>> getAll(Pageable pageable, @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        final Page<InvoiceDto> invoiceDtos = invoiceService.findAllByQuery(pageable, query);
        LOGGER.info("Return carList.size:{}", invoiceDtos.getNumber());
        return new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/list")
    public ResponseEntity<Page<InvoiceDto>> getAll(@CurrentUser UserPrincipal user, Pageable pageable) {
        LOGGER.info("REST request. Path:/invoice method: GET.");
        final Page<InvoiceDto> invoiceDtos = invoiceService
                .findAllByRequestClientCompanyFromIdAndStatus(user.getClientCompanyId(), Invoice.Status.COMPLETED, pageable);
        LOGGER.info("Return carList.size:{}", invoiceDtos.getNumber());
        return new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
    }

}
