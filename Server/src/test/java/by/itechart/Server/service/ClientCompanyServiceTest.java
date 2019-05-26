package by.itechart.Server.service;

import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.util.ClientCompanyUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Optional;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ClientCompanyServiceTest {
    @Resource
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Resource
    private ClientCompanyService clientCompanyService;

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testSave() {
        clientCompanyService.save(ClientCompanyUtil.createClientCompany());
    }

    @Test
    public void testFindAll() {
//        Collection<ClientCompany> clientCompanies = clientCompanyService.findAll();
//        Assert.assertTrue(clientCompanies.size() > 0);
    }

    @Test
    public void testGetOne() {
        Optional<ClientCompany> clientCompany=clientCompanyService.findById(1);
        Assert.assertNotNull(clientCompany);
    }
}
