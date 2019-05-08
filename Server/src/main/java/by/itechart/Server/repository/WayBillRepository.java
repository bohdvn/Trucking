package by.itechart.Server.repository;

import by.itechart.Server.entity.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayBillRepository extends JpaRepository<WayBill,Integer> {
}
