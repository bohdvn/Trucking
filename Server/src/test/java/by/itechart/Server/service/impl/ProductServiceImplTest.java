package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Product;
import by.itechart.Server.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService=new ProductServiceImpl(productRepository);

    private Product product;

    @Before
    public void setUp() {
        product=new Product();
        product.setId(1);
        product.setAmount(123);
        product.setName("test");
        product.setType("type");
        product.setPrice(321);
        product.setStatus(Product.Status.DELIVERED);
    }

    @After
    public void tearDown() {
        product=null;
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));
//        assertEquals(Arrays.asList(product),productService.findAll());
    }

    @Test
    public void findById() {
        Optional<Product> optional=Optional.of(product);
        Mockito.when(productRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(optional,productService.findById(anyInt()));
    }

    @Test
    public void deleteById() {
    }
}