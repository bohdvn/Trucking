package by.itechart.Server.repository;

import by.itechart.Server.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint,Integer> {
}
