package com.purebros.care.customer.main.datasources.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "findByYearProcedure",
                procedureName = "FIND_CAR_BY_YEAR",
                resultClasses = {Car.class},
                parameters = {
                        @StoredProcedureParameter(
                                name = "p_year",
                                type = Integer.class,
                                mode = ParameterMode.IN) })
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    public Car() {
    }

    public Car(String model, Integer year) {
        this.model = model;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }


    public Integer getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
