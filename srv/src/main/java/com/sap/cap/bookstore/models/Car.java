package com.sap.cap.bookstore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Car implements Serializable {
    private static Logger log = LoggerFactory.getLogger(Car.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id") Long id;
    @JsonProperty("VIN") String VIN;
    @JsonProperty("wheelsNumber") int wheelsNumber;
    @JsonProperty("colour") String colour;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Car car = (Car) o;
        return getId() != null && Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PrePersist
    public void logNewUserAttempt() {
        log.info("Attempting to add new user with username: " + VIN);
    }

    @PostPersist
    public void logNewUserAdded() {
        log.info("Added user '" + VIN + "' with ID: " + id);
    }

    @PreRemove
    public void logUserRemovalAttempt() {
        log.info("Attempting to delete user: " + VIN);
    }

    @PostRemove
    public void logUserRemoval() {
        log.info("Deleted user: " + VIN);
    }

    @PreUpdate
    public void logUserUpdateAttempt() {
        log.info("Attempting to update user: " + VIN);
    }

    @PostUpdate
    public void logUserUpdate() {
        log.info("Updated user: " + VIN);
    }

}
