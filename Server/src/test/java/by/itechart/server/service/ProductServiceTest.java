package by.itechart.server.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ProductServiceTest {
    @Resource
    private EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    @Resource
    private ProductService productService;

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

//    @Test
//    public void testSave() {
//        productService.save(ProductUtil.createProduct());
//    }

//    @Test
//    public void testGetOne() {
//        Optional<Product> product=productService.findById(1);
//        Assert.assertNotNull(product);
//    }
}
