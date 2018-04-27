package com.purebros.care.customer.main.datasources.user.repository;

import com.purebros.care.customer.main.datasources.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
