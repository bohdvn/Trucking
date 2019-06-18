package by.itechart.server.repository;

import by.itechart.server.entity.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WayBillRepository extends JpaRepository<WayBill,Integer> {
    Page<WayBill> findAllByInvoiceRequestDriverIdAndStatus(int id, WayBill.Status status, Pageable pageable);

    Optional<WayBill> findByIdAndInvoiceRequestDriverId(int id, Integer integer);
}
