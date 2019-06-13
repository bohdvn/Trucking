package by.itechart.Server.service.impl;

import by.itechart.Server.dto.CheckpointDto;
import by.itechart.Server.entity.Checkpoint;
import by.itechart.Server.repository.CheckpointRepository;
import by.itechart.Server.service.CheckpointService;
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
