package com.purebros.care.customer.main.datasources.test.repository;

import com.purebros.care.customer.main.datasources.test.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
