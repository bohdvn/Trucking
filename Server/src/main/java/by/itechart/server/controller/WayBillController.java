package by.itechart.server.controller;

import by.itechart.server.dto.WayBillDto;
import by.itechart.server.service.WayBillService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/waybill")
public class WayBillController {
    private WayBillService wayBillService;

    public WayBillController(WayBillService wayBillService) {
        this.wayBillService = wayBillService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WayBillController.class);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN') or hasAuthority('DRIVER')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@Valid @RequestBody WayBillDto wayBill) {
        LOGGER.info("REST request. Path:/waybill method: POST. waybill: {}", wayBill);
        wayBillService.save(wayBill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody WayBillDto wayBill) {
        LOGGER.info("REST request. Path:/waybill method: POST. waybill: {}", wayBill);
        wayBillService.save(wayBill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/waybill/{} method: GET.", id);
        final WayBillDto wayBillDto = wayBillService.findById(id);
        return Objects.nonNull(wayBillDto) ?
                ResponseEntity.ok().body(wayBillDto) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @DeleteMapping("/{selectedWaybills}")
    public ResponseEntity<?> remove(@PathVariable("selectedWaybills") String selectedWaybills) {
        LOGGER.info("REST request. Path:/waybill/{} method: DELETE.", selectedWaybills);
        final String delimeter = ",";
        final String[] waybillsId = selectedWaybills.split(delimeter);
        for (String id : waybillsId) {
            wayBillService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/")
    public ResponseEntity<Page<WayBillDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/waybill method: GET.");
        final Page<WayBillDto> wayBillsDto = wayBillService.findAll(pageable);
        LOGGER.info("Return waybillList.size:{}", wayBillsDto.getNumber());
        return wayBillsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(wayBillsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<WayBillDto>> getAll(Pageable pageable,  @PathVariable("query") String query ) {
        LOGGER.info("REST request. Path:/waybill method: GET.");
        final Page<WayBillDto> wayBillsDto = wayBillService.findAllByQuery(pageable, query);
        LOGGER.info("Return waybillList.size:{}", wayBillsDto.getNumber());
        return wayBillsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(wayBillsDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
