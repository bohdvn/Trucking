package by.itechart.server.controller;

import by.itechart.server.dto.UserDto;
import by.itechart.server.dto.WayBillDto;
import by.itechart.server.security.CurrentUser;
import by.itechart.server.security.UserPrincipal;
import by.itechart.server.service.InvoiceService;
import by.itechart.server.service.UserService;
import by.itechart.server.service.WayBillService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/waybill")
public class WayBillController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WayBillController.class);
    private WayBillService wayBillService;
    private UserService userService;

    public WayBillController(WayBillService wayBillService, UserService userService, InvoiceService invoiceService) {
        this.wayBillService = wayBillService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('DRIVER')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody WayBillDto wayBill) {
        LOGGER.info("REST request. Path:/waybill method: POST. waybill: {}", wayBill);
        wayBillService.save(wayBill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    @PostMapping("")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody WayBillDto wayBill) {
        LOGGER.info("REST request. Path:/waybill method: POST. waybill: {}", wayBill);
        final UserDto manager = userService.findById(userPrincipal.getId());
        wayBill.getInvoice().setManager(manager);
        wayBillService.save(wayBill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DRIVER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/waybill/{} method: GET.", id);
        final WayBillDto wayBillDto = wayBillService.findByIdAndInvoiceRequestDriverId(id, userPrincipal.getId());
        return Objects.nonNull(wayBillDto) ?
                ResponseEntity.ok().body(wayBillDto) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/{selectedWaybills}")
//    public ResponseEntity<?> remove(@PathVariable("selectedWaybills") String selectedWaybills) {
//        LOGGER.info("REST request. Path:/waybill/{} method: DELETE.", selectedWaybills);
//        final String delimeter = ",";
//        final String[] waybillsId = selectedWaybills.split(delimeter);
//        for (String id : waybillsId) {
//            wayBillService.deleteById(Integer.valueOf(id));
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PreAuthorize("hasAuthority('DRIVER') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/")
    public ResponseEntity<Page<WayBillDto>> getAll(@CurrentUser UserPrincipal user, Pageable pageable) {
        LOGGER.info("REST request. Path:/waybill method: GET.");
        final Page<WayBillDto> wayBillsDto =
                wayBillService.findAllByInvoiceRequestDriverId(user.getId(), pageable);
        LOGGER.info("Return waybillList.size:{}", wayBillsDto.getNumber());
        return new ResponseEntity<>(wayBillsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DRIVER') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<WayBillDto>> getAll(@CurrentUser UserPrincipal user, Pageable pageable,
                                                   @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/waybill method: GET.");
        final Page<WayBillDto> wayBillsDto = wayBillService.findAllByInvoiceRequestDriverId(user.getId(),
                pageable, query);
        LOGGER.info("Return waybillList.size:{}", wayBillsDto.getNumber());
        return new ResponseEntity<>(wayBillsDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
