package by.itechart.server.service.impl;

import by.itechart.server.dto.ProductDto;
import by.itechart.server.entity.Product;
import by.itechart.server.repository.ProductRepository;
import by.itechart.server.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto save(final ProductDto productDto) {
        if (Objects.isNull(productDto.getId())) {
            return productRepository.save(productDto.transformToEntity()).transformToDto();
            //костыыыыль
        } else {
            final Product entity = productRepository.findById(productDto.getId()).orElseThrow(IllegalStateException::new);
            entity.setAmount(productDto.getAmount());
            entity.setLostAmount(productDto.getLostAmount());
            entity.setName(productDto.getName());
            entity.setPrice(productDto.getPrice());
            entity.setStatus(productDto.getStatus());
            entity.setType(productDto.getType());
            return productRepository.save(entity).transformToDto();
        }

    }

    @Override
    public Page<ProductDto> findAll(final Pageable pageable) {
        final Page<Product> products = productRepository.findAll(pageable);
        return new PageImpl<>(products.stream().map(Product::transformToDto)
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Override
    public ProductDto findById(final int id) {
        return productRepository.findById(id).isPresent() ?
                productRepository.findById(id).get().transformToDto() : null;
    }

    @Override
    public void deleteById(final int id) {
        productRepository.deleteById(id);
    }

}
