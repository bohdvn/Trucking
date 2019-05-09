package by.itechart.Server.service.impl;

import by.itechart.Server.entity.Product;
import by.itechart.Server.repository.ProductRepository;
import by.itechart.Server.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(int id) { return  productRepository.findById(id);}

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
