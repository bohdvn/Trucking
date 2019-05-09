package by.itechart.Server.service;

import by.itechart.Server.entity.Checkpoint;

import java.util.List;
import java.util.Optional;

public interface CheckpointService {

    void save(Checkpoint checkpoint);

    List<Checkpoint> findAll();

    Optional<Checkpoint> findById(int id);

    void deleteById(int id);
}
