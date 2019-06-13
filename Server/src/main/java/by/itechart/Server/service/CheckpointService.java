package by.itechart.Server.service;

import by.itechart.Server.dto.CheckpointDto;
import by.itechart.Server.entity.Checkpoint;

import java.util.List;
import java.util.Optional;

public interface CheckpointService {

    void save(CheckpointDto checkpointDto);

    List<CheckpointDto> findAll();

    CheckpointDto findById(int id);

    void deleteById(int id);
}
