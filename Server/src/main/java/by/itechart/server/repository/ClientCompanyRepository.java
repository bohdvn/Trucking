package by.itechart.server.repository;

import by.itechart.server.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientCompanyRepository extends JpaRepository<ClientCompany,Integer> {

    //Page<ClientCompany> findClientCompaniesByCompanyType(ClientCompany.CompanyType companyType, Pageable pageable);
}
