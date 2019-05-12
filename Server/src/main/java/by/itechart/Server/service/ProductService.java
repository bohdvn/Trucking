package by.itechart.Server.service;

import by.itechart.Server.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void save(Product product);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(int id);

    void deleteById(int id);
}
