package by.itechart.Server.controller;

import by.itechart.Server.service.ClientCompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientCompanyController {
    private ClientCompanyService clientCompanyService;

    public ClientCompanyController(ClientCompanyService clientCompanyService){
        this.clientCompanyService=clientCompanyService;
    }
}
