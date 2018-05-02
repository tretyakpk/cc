package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.user.dao.UserDao;
import com.purebros.care.customer.main.datasources.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Optional;


@Component
@RestController()
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;

    @RequestMapping(value = "/{name}/{password}", method = RequestMethod.GET)
    public ResponseEntity<User> user(@PathVariable("name") String userName, @PathVariable("password") String password, Principal principal){
        UserDao userDao =  new UserDao();
        userDao.setDataSource(userDataSource);
        Optional<User> user  = (userDao.findUser(userName, password));

        System.out.println(user);

        System.out.println(principal.toString());

        if(user.isPresent())
            return new ResponseEntity(user, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }
}
