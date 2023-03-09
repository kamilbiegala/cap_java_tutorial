package com.sap.cap.bookstore.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue
    private Long Id;
    private String VIN;
    private int wheelsNumber;
    private String colour;
}
