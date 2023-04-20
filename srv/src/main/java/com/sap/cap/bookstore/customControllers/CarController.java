package com.sap.cap.bookstore.customControllers;

import com.sap.cap.bookstore.models.Car;
import com.sap.cap.bookstore.repository.CarRepository;
import com.sap.cap.bookstore.services.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    private final CarRepository repository;
    private final CarService carService;


    public CarController(CarRepository repository, CarService carService) {
        this.repository = repository;
        this.carService = carService;
    }

    @GetMapping(path = "/cars")
    public List<Car> all() {
        return repository.findAll();
    }

    @GetMapping(path = "/cars/{id}")
    public Car byId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping(path = "/cars")
    public Car newCar(@RequestBody Car newCar) {
        return carService.sendNewCarToEdit(newCar);
    }

    @PostMapping(path = "/cars2")
    public Car newCar2(@RequestBody Car newCar) {
        Car car2 = new Car();
        return carService.saveCar(car2);
    }

}
