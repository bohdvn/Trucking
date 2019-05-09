package by.itechart.Server.controller;

import by.itechart.Server.dto.ProductDto;
import by.itechart.Server.entity.Product;
import by.itechart.Server.service.ProductService;
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

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody Product product){
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<Product> product = productService.findById(id);
        return product.isPresent()?
                ResponseEntity.ok().body(product.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll() {
        List<Product> products = productService.findAll();
        List<ProductDto> productsDto = products.stream().map(Product::transform).collect(Collectors.toList());
        return products.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(productsDto, HttpStatus.OK);
    }
}
