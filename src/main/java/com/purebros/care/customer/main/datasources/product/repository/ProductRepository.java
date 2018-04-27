package com.purebros.care.customer.main.datasources.product.repository;

import com.purebros.care.customer.main.datasources.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
