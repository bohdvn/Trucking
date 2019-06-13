package by.itechart.Server.service;

import by.itechart.Server.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto save(final ProductDto product);

    Page<ProductDto> findAll(final Pageable pageable);

    ProductDto findById(final int id);

    void deleteById(final int id);
}
