package com.sap.cap.bookstore.services;

import com.sap.cap.bookstore.configuration.RabbitConfiguration;
import com.sap.cap.bookstore.models.Car;
import com.sap.cap.bookstore.repository.CarRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarService {
    private final CarRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public CarService(CarRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Car sendNewCarToEdit(Car newCar) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.topicExchangeName, "send.newCar", newCar);
        return newCar;
    }

    public Car saveCar(Car newCar) {
        return repository.save(newCar);
    }

}
