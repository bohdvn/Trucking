package by.itechart.server.repository;

import by.itechart.server.entity.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayBillRepository extends JpaRepository<WayBill,Integer> {
}
