package com.sap.cap.bookstore.components;

import com.sap.cap.bookstore.configuration.RabbitConfiguration;
import com.sap.cap.bookstore.models.Car;
import com.sap.cap.bookstore.services.CarService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@RabbitListener(queues = {"queue-name2"})
public class RabbitReceiver {
    @Autowired
    private ApplicationContext context;
//    private CarRepository carRepository;
    private CarService carService;

    public RabbitReceiver() {
    }

    @RabbitHandler
    @Transactional
    public void receive(String input) {
        System.out.println("Received message from " + RabbitConfiguration.queueName2 + ": " + input);
        Car car2 = new Car();
        carService.saveCar(car2);
//        carRepository.save(car2);
    }

    @PostConstruct
    private void init() {
//        carRepository = context.getBean(CarRepository.class);
        carService = context.getBean(CarService.class);
    }

}
