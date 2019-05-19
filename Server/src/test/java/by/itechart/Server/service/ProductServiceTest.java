package by.itechart.Server.service;

import by.itechart.Server.entity.Product;
import by.itechart.Server.util.ProductUtil;
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
import java.util.Optional;

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

    @Test
    public void testSave() {
        productService.save(ProductUtil.createProduct());
    }

    @Test
    public void testFindAll() {
//        Collection<Product> products = productService.findAll();
//        Assert.assertTrue(products.size() > 0);
    }

    @Test
    public void testGetOne() {
        Optional<Product> product=productService.findById(1);
        Assert.assertNotNull(product);
    }
}
