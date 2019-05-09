package by.itechart.Server.service;

import by.itechart.Server.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void save(Product product);

    List<Product> findAll();

    Optional<Product> findById(int id);

    void deleteById(int id);
}
