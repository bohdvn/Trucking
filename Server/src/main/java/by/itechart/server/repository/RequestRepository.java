package by.itechart.server.repository;

import by.itechart.server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    Page<Request> findAllByClientCompanyFromId(int id,Pageable pageable);

    Page<Request> findAllByClientCompanyFromIdAndStatus(int id, Request.Status status, Pageable pageable);
}
