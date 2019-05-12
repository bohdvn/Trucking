package by.itechart.Server.repository;

import by.itechart.Server.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
    @Override
    Page<Car> findAll(Pageable pageable);
}
