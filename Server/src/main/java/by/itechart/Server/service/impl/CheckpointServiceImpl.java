package by.itechart.Server.service.impl;

import by.itechart.Server.repository.CheckpointRepository;
import by.itechart.Server.service.CheckpointService;
import org.springframework.stereotype.Service;

@Service
public class CheckpointServiceImpl implements CheckpointService {
    private CheckpointRepository checkpointRepository;

    public CheckpointServiceImpl(CheckpointRepository checkpointRepository){
        this.checkpointRepository=checkpointRepository;
    }
}
