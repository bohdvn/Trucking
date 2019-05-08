package by.itechart.Server.controller;

import by.itechart.Server.service.WayBillService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waybill")
public class WayBillController {
    private WayBillService wayBillService;

    public WayBillController(WayBillService wayBillService){
        this.wayBillService=wayBillService;
    }
}
