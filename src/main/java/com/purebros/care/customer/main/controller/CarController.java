package com.purebros.care.customer.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@Component
@RestController()
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    @Qualifier("testDataSource")
    private DataSource testDataSource;


    @RequestMapping(value = "/test/{year}", method = RequestMethod.GET)
    public String testGetCarsFromSP(@PathVariable("year") Integer year) {

        SimpleJdbcCall call = new SimpleJdbcCall(testDataSource).withProcedureName("FIND_CAR_BY_YEAR");

        SqlParameterSource p_year = new MapSqlParameterSource().addValue("p_year", year);

        Map<String, Object> out = call.execute(p_year);

        out.forEach((k, v) -> System.out.println(k + " " + v));

        return "ok";
    }
}
