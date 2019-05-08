package by.itechart.Server.controller;

import by.itechart.Server.service.CarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {
    private CarService carService;

    public CarController(CarService carService){
        this.carService=carService;
    }
}
