package by.itechart.Server.controller;

import by.itechart.Server.dto.ClientCompanyDto;
import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.service.ClientCompanyService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientCompanyController {
    private ClientCompanyService clientCompanyService;

    public ClientCompanyController(ClientCompanyService clientCompanyService){
        this.clientCompanyService=clientCompanyService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCompanyController.class);

    @PutMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ClientCompany clientCompany){
        LOGGER.info("REST request. Path:/client method: POST. client: {}", clientCompany);
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/client/{} method: GET.", id);
        Optional<ClientCompany> clientCompany = clientCompanyService.findById(id);
        return clientCompany.isPresent()?
                ResponseEntity.ok().body(clientCompany.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/client/{} method: DELETE.", id);
        clientCompanyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientCompanyDto>> getAll() {
        LOGGER.info("REST request. Path:/client method: GET.");
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        List<ClientCompanyDto> clientCompaniesDto = clientCompanies.stream().map(ClientCompany::transform).collect(Collectors.toList());
        LOGGER.info("Return clientCompanyList.size:{}", clientCompaniesDto.size());
        return clientCompanies.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(clientCompaniesDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
