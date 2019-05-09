package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Checkpoint;
import by.itechart.Server.repository.CheckpointRepository;
import by.itechart.Server.service.CheckpointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckpointServiceImpl implements CheckpointService {
    private CheckpointRepository checkpointRepository;

    public CheckpointServiceImpl(CheckpointRepository checkpointRepository){
        this.checkpointRepository=checkpointRepository;
    }

    @Override
    public void save(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);
    }

    @Override
    public List<Checkpoint> findAll() {
        return checkpointRepository.findAll();
    }

    @Override
    public Optional<Checkpoint> findById(int id) { return checkpointRepository.findById(id); }

    @Override
    public void deleteById(int id) {
        checkpointRepository.deleteById(id);
    }
}
