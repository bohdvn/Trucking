package by.itechart.Server.controller;

import by.itechart.Server.dto.CheckpointDto;
import by.itechart.Server.entity.Checkpoint;
import by.itechart.Server.service.CheckpointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkpoint")
public class CheckpointController {
    private CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService){
        this.checkpointService=checkpointService;
    }

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Checkpoint checkpoint){
        checkpointService.save(checkpoint);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<Checkpoint> checkpoint = checkpointService.findById(id);
        return checkpoint.isPresent()?
                ResponseEntity.ok().body(checkpoint.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        checkpointService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CheckpointDto>> getAll() {
        List<Checkpoint> checkpoints = checkpointService.findAll();
        List<CheckpointDto> checkpointsDto = checkpoints.stream().map(Checkpoint::transform).collect(Collectors.toList());
        return checkpoints.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(checkpointsDto, HttpStatus.OK);
    }
}
