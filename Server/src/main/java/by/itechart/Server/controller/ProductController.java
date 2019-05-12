package by.itechart.Server.controller;

import by.itechart.Server.dto.ProductDto;
import by.itechart.Server.entity.Product;
import by.itechart.Server.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Product product){
        LOGGER.info("REST request. Path:/product method: POST. product: {}", product);
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/product/{} method: GET.", id);
        Optional<Product> product = productService.findById(id);
        return product.isPresent()?
                ResponseEntity.ok().body(product.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/product/{} method: DELETE.", id);
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProductDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/product method: GET.");
        Page<Product> products = productService.findAll(pageable);
        Page<ProductDto> productsDto = new PageImpl<>(products.stream().map(Product::transform).collect(Collectors.toList()));
        LOGGER.info("Return productList.size:{}", productsDto.getNumber());
        return products.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(productsDto, HttpStatus.OK);
    }
}
