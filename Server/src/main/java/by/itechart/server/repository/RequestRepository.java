package by.itechart.server.repository;

import by.itechart.server.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {

    Optional<Request> findByIdAndClientCompanyFromId(Integer id, Integer clientId);

    Page<Request> findAllByClientCompanyFromId(int id, Pageable pageable);

    Page<Request> findAllByClientCompanyFromIdAndStatus(int id, Request.Status status, Pageable pageable);
}
