package by.itechart.Server.controller;

import by.itechart.Server.dto.ClientCompanyDto;
import by.itechart.Server.entity.ClientCompany;
import by.itechart.Server.service.ClientCompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/")
    public ResponseEntity<?> create(@RequestBody ClientCompany clientCompany){
        clientCompanyService.save(clientCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id){
        Optional<ClientCompany> clientCompany = clientCompanyService.findById(id);
        return clientCompany.isPresent()?
                ResponseEntity.ok().body(clientCompany.get().transform()):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id){
        clientCompanyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientCompanyDto>> getAll() {
        List<ClientCompany> clientCompanies = clientCompanyService.findAll();
        List<ClientCompanyDto> clientCompaniesDto = clientCompanies.stream().map(ClientCompany::transform).collect(Collectors.toList());
        return clientCompanies.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(clientCompaniesDto, HttpStatus.OK);
    }
}
