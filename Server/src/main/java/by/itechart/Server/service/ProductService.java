package by.itechart.Server.service;

import by.itechart.Server.dto.ProductDto;
import by.itechart.Server.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    ProductDto save(final ProductDto product);

    Page<ProductDto> findAll(final Pageable pageable);

    ProductDto findById(final int id);

    void deleteById(final int id);
}
