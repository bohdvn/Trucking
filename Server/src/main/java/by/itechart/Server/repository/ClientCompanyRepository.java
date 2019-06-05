package by.itechart.Server.repository;

import by.itechart.Server.entity.ClientCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientCompanyRepository extends JpaRepository<ClientCompany,Integer> {

    Page<ClientCompany> findClientCompaniesByCompanyType(ClientCompany.CompanyType companyType, Pageable pageable);
}
