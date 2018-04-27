package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.user.entity.User;
import com.purebros.care.customer.main.datasources.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

@Component
@RestController()
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;

    @Autowired
    @Qualifier("userEntityManager")
    private EntityManager userEntityManager;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity user(@PathVariable("id") Integer id){

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(userDataSource).withProcedureName("Find_User");

        simpleJdbcCall.execute();

        try {
            Optional<User> user = userRepository.findById(id);
            System.out.println(user);
            return user.map(user1 -> new ResponseEntity<>(user1, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e){ System.out.println(e.getMessage()); }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
