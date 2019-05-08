package by.itechart.Server.controller;

import by.itechart.Server.service.CheckpointService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkpoint")
public class CheckpointController {
    private CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService){
        this.checkpointService=checkpointService;
    }
}
