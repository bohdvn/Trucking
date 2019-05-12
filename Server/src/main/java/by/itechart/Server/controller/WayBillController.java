package by.itechart.Server.controller;

import by.itechart.Server.dto.WayBillDto;
import by.itechart.Server.entity.WayBill;
import by.itechart.Server.service.WayBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/waybill")
public class WayBillController {
    private WayBillService wayBillService;

    public WayBillController(WayBillService wayBillService){
        this.wayBillService=wayBillService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WayBillController.class);

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody WayBill wayBill){
        LOGGER.info("REST request. Path:/waybill method: POST. waybill: {}", wayBill);
        wayBillService.save(wayBill);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/waybill/{} method: GET.", id);
        Optional<WayBill> wayBill = wayBillService.findById(id);
        return wayBill.isPresent()?
                ResponseEntity.ok().body(wayBill.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/waybill/{} method: DELETE.", id);
        wayBillService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WayBillDto>> getAll() {
        LOGGER.info("REST request. Path:/waybill method: GET.");
        List<WayBill> wayBills = wayBillService.findAll();
        List<WayBillDto> wayBillsDto = wayBills.stream().map(WayBill::transform).collect(Collectors.toList());
        LOGGER.info("Return waybillList.size:{}", wayBillsDto.size());
        return wayBills.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(wayBillsDto, HttpStatus.OK);
    }
}
