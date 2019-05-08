package by.itechart.Server.repository;

import by.itechart.Server.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCompanyRepository extends JpaRepository<ClientCompany,Integer> {
}
