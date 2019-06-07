package by.itechart.Server.service;

import by.itechart.Server.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(int id);

    void deleteById(int id);
}
