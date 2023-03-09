package com.sap.cap.bookstore.repository;

import com.sap.cap.bookstore.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findByColourStartsWithIgnoreCase(String colour);
}
