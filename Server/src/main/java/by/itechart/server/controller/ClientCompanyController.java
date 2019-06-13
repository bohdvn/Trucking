package by.itechart.server.controller;

import by.itechart.server.dto.ClientCompanyDto;
import by.itechart.server.security.CurrentUser;
import by.itechart.server.security.UserPrincipal;
import by.itechart.server.service.ClientCompanyService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientCompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCompanyController.class);
    private ClientCompanyService clientCompanyService;

    public ClientCompanyController(ClientCompanyService clientCompanyService) {
        this.clientCompanyService = clientCompanyService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody ClientCompanyDto clientCompanyDto) {
        LOGGER.info("REST request. Path:/client method: PUT. client: {}", clientCompanyDto);
        clientCompanyService.save(clientCompanyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody ClientCompanyDto clientCompanyDto) {
        LOGGER.info("REST request. Path:/client method: POST. client: {}", clientCompanyDto);
        clientCompanyService.save(clientCompanyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN' or hasAuthority('SYSADMIN'))")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/client/{} method: GET.", id);
        final ClientCompanyDto clientCompanyDto = clientCompanyService.findById(id);
        return clientCompanyDto == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(clientCompanyDto);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{selectedClients}")
    public ResponseEntity<?> remove(@CurrentUser UserPrincipal userPrincipal, @PathVariable("selectedClients") String selectedClients) {
        LOGGER.info("REST request. Path:/client/{} method: DELETE.", selectedClients);
        final String delimeter = ",";
        final String[] clientsId = selectedClients.split(delimeter);
        for (String id : clientsId) {
            clientCompanyService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<ClientCompanyDto>> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
        LOGGER.info("REST request. Path:/client method: GET.");
        Page<ClientCompanyDto> clientCompaniesDto = clientCompanyService.findAll(pageable);
        LOGGER.info("Return clientCompanyList.size:{}", clientCompaniesDto.getNumber());
        return clientCompaniesDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(clientCompaniesDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
