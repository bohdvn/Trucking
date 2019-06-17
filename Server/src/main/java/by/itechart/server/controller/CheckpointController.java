package by.itechart.server.controller;

import by.itechart.server.dto.CheckpointDto;
import by.itechart.server.service.CheckpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkpoint")
public class CheckpointController {
    private CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService){
        this.checkpointService=checkpointService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckpointController.class);

    @PreAuthorize("hasAuthority('DRIVER')")
    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody CheckpointDto checkpointDto){
        LOGGER.info("REST request. Path:/checkpoint method: POST. checkpoint: {}", checkpointDto);
        checkpointService.save(checkpointDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/checkpoint/{} method: GET.", id);
        final CheckpointDto checkpointDto = checkpointService.findById(id);
        return checkpointDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND):
                ResponseEntity.ok().body(checkpointDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/checkpoint/{} method: DELETE.", id);
        checkpointService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CheckpointDto>> getAll() {
        LOGGER.info("REST request. Path:/checkpoint method: GET.");
        List<CheckpointDto> checkpointsDto = checkpointService.findAll();
        LOGGER.info("Return checkpointList.size:{}", checkpointsDto.size());
        return checkpointsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(checkpointsDto, HttpStatus.OK);
    }
}
