package by.itechart.server.service.impl;

import by.itechart.server.dto.CheckpointDto;
import by.itechart.server.entity.Checkpoint;
import by.itechart.server.repository.CheckpointRepository;
import by.itechart.server.service.CheckpointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckpointServiceImpl implements CheckpointService {

    private CheckpointRepository checkpointRepository;

    public CheckpointServiceImpl(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    @Override
    public void save(CheckpointDto checkpointDto) {
        checkpointRepository.save(checkpointDto.transformToEntity());
    }

    @Override
    public List<CheckpointDto> findAll() {
        return checkpointRepository.findAll().stream().map(Checkpoint::transformToDto).collect(Collectors.toList());
    }

    @Override
    public CheckpointDto findById(int id) {
        return checkpointRepository.findById(id).isPresent() ?
                checkpointRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(int id) {
        checkpointRepository.deleteById(id);
    }
}
