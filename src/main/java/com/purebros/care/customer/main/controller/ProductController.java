package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.product.entity.Product;
import com.purebros.care.customer.main.datasources.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@RestController()
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    @Qualifier("productEntityManager")
    private EntityManager productEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> product(@PathVariable("id") Integer id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(car1 -> new ResponseEntity<>(car1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
