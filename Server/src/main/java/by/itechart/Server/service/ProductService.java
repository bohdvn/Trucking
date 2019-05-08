package by.itechart.Server.service;

import by.itechart.Server.entity.Product;

import java.util.List;

public interface ProductService {
    void save(Product product);

    List<Product> findAll();

    Product getOne(int id);

    void delete(Product product);

    void deleteById(int id);
}
