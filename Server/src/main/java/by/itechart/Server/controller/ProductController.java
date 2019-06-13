package by.itechart.Server.controller;

import by.itechart.Server.dto.ProductDto;
import by.itechart.Server.service.ProductService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto) {
        LOGGER.info("REST request. Path:/product method: POST. product: {}", productDto);
        final ProductDto save = productService.save(productDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/product/{} method: GET.", id);
        final ProductDto productDto = productService.findById(id);
        return productDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(productDto);

    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/product/{} method: DELETE.", id);
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @DeleteMapping("/{selectedProducts}")
    public ResponseEntity<?> remove(@PathVariable("selectedProducts") String selectedProducts) {
        LOGGER.info("REST request. Path:/product/{} method: DELETE.", selectedProducts);
        final String delimeter = ",";
        final String[] productsId = selectedProducts.split(delimeter);
        for (String id : productsId) {
            productService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<ProductDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/product method: GET.");
        Page<ProductDto> productsDto = productService.findAll(pageable);
        LOGGER.info("Return productList.size:{}", productsDto.getNumber());
        return productsDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
