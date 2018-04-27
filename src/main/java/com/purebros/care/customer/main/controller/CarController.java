package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.test.entity.Car;
import com.purebros.care.customer.main.datasources.test.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RestController()
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    @Qualifier("testEntityManager")
    private EntityManager testEntityManager;

    @Autowired
    @Qualifier("testDataSource")
    private DataSource testDataSource;

    @Autowired
    private CarRepository carRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Car> car(@PathVariable("id") Integer id){
        Optional<Car> car = carRepository.findById(id);
        return car.map(car1 -> new ResponseEntity<>(car1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @RequestMapping(value = "/year/{year}", method = RequestMethod.GET)
    public ResponseEntity<List> getCarsFromSP(@PathVariable("year") Integer year){

        return new ResponseEntity<>(testEntityManager
                .createNamedStoredProcedureQuery("findByYearProcedure")
                .setParameter("p_year", year)
                .getResultList(),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/test/{year}", method = RequestMethod.GET)
    public ResponseEntity<List> testGetCarsFromSP(@PathVariable("year") Integer year){

        SimpleJdbcCall call = new SimpleJdbcCall(testDataSource).withProcedureName("FIND_CAR_BY_YEAR");

        SqlParameterSource p_year = new MapSqlParameterSource().addValue("p_year", year);

        Map<String, Object> out = call.execute(p_year);

        out.forEach((k, v) -> System.out.println(k + " " + v));

        return new ResponseEntity<>(testEntityManager
                .createNamedStoredProcedureQuery("findByYearProcedure")
                .setParameter("p_year", year)
                .getResultList(),
                HttpStatus.OK
        );
    }
}
