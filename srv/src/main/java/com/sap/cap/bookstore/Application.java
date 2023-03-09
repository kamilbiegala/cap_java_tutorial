package com.sap.cap.bookstore;

import com.sap.cap.bookstore.models.Car;
import com.sap.cap.bookstore.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CarRepository repository) {
		return (args) -> {
			// save a couple of customers
			var car = new Car();
			car.setColour("black");
			repository.save(car);

			// fetch all customers
			log.info("Cars found with findAll():");
			log.info("-------------------------------");
			for (Car customer : repository.findAll()) {
				log.info("Tu żyję jeszcze");
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Car customer = repository.findById(1L).get();
			log.info("Cars found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Cars found with findByColourStartsWithIgnoreCase('BlAck'):");
			log.info("--------------------------------------------");
			for (Car bauer : repository
					.findByColourStartsWithIgnoreCase("BlAck")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}

}
