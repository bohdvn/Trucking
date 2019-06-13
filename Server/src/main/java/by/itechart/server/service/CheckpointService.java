package by.itechart.server.service;

import by.itechart.server.dto.CheckpointDto;

import java.util.List;

public interface CheckpointService {

    void save(CheckpointDto checkpointDto);

    List<CheckpointDto> findAll();

    CheckpointDto findById(int id);

    void deleteById(int id);
}
