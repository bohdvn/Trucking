package by.itechart.server.repository;

import by.itechart.server.entity.Invoice;
import by.itechart.server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
    Page<Invoice> findAllByRequestClientCompanyFromIdAndStatus(final int id, Invoice.Status status, Pageable pageable);
}
