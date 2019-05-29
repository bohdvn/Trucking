package by.itechart.Server.controller;

import by.itechart.Server.dto.ClientCompanyDto;
import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.security.CurrentUser;
import by.itechart.Server.security.UserPrincipal;
import by.itechart.Server.service.ClientCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> edit(@CurrentUser UserPrincipal userPrincipal, @RequestBody ClientCompany clientCompany){
        LOGGER.info("REST request. Path:/client method: PUT. client: {}", clientCompany);
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal,@RequestBody ClientCompany clientCompany){
        LOGGER.info("REST request. Path:/client method: POST. client: {}", clientCompany);
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal userPrincipal,@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/client/{} method: GET.", id);
        Optional<ClientCompany> clientCompany = clientCompanyService.findById(id);
        return clientCompany.isPresent()?
                ResponseEntity.ok().body(clientCompany.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@CurrentUser UserPrincipal userPrincipal,@PathVariable("id") int id){
        LOGGER.info("REST request. Path:/client/{} method: DELETE.", id);
        clientCompanyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ClientCompanyDto>> getAll(@CurrentUser UserPrincipal userPrincipal) {
        LOGGER.info("REST request. Path:/client method: GET.");
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        List<ClientCompanyDto> clientCompaniesDto = clientCompanies.stream().map(ClientCompany::transform).collect(Collectors.toList());
        LOGGER.info("Return clientCompanyList.size:{}", clientCompaniesDto.size());
        return clientCompanies.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(clientCompaniesDto, HttpStatus.OK);
    }
}
