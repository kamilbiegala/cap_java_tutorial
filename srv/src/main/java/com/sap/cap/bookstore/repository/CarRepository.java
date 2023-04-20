package com.sap.cap.bookstore.repository;

import com.sap.cap.bookstore.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByColourStartsWithIgnoreCase(String colour);

    @Override
    @Async
    <S extends Car> S save(S entity);
}
