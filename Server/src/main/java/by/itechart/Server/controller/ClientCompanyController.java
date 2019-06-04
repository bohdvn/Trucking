package by.itechart.Server.controller;

import by.itechart.Server.dto.ClientCompanyDto;
import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.service.ClientCompanyService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientCompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCompanyController.class);
    private ClientCompanyService clientCompanyService;

    public ClientCompanyController(ClientCompanyService clientCompanyService) {
        this.clientCompanyService = clientCompanyService;
    }

    @PutMapping("/")
    public ResponseEntity<?> edit(@Valid @RequestBody ClientCompany clientCompany) {
        LOGGER.info("REST request. Path:/client method: POST. client: {}", clientCompany);
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ClientCompany clientCompany) {
        LOGGER.info("REST request. Path:/client method: POST. client: {}", clientCompany);
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/client/{} method: GET.", id);
        Optional<ClientCompany> clientCompany = clientCompanyService.findById(id);
        return clientCompany.isPresent() ?
                ResponseEntity.ok().body(clientCompany.get().transform()) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{selectedClients}")
    public ResponseEntity<?> remove(@PathVariable("selectedClients") String selectedClients) {
        LOGGER.info("REST request. Path:/client/{} method: DELETE.", selectedClients);
        final String delimeter = ",";
        final String[] clientsId = selectedClients.split(delimeter);
        for (String id : clientsId) {
            clientCompanyService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ClientCompanyDto>> getAll(Pageable pageable, ClientCompany.CompanyType companyType) {
        LOGGER.info("REST request for list of companies. Path:/client/list method: GET.");
        Page<ClientCompany> clientCompanies = clientCompanyService.findByCompanyType(companyType, pageable);
        Page<ClientCompanyDto> clientCompaniesDto = new PageImpl<>(clientCompanies.stream()
                .map(ClientCompany::transform)
                .sorted(Comparator.comparing(ClientCompanyDto::getName))
                .collect(Collectors.toList()), pageable, clientCompanies.getTotalElements());
        LOGGER.info("Return companyList.size:{}", clientCompaniesDto.getNumber());
        return clientCompanies.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(clientCompaniesDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
