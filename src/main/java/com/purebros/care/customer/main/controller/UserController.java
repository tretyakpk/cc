package com.purebros.care.customer.main.controller;

import com.purebros.care.customer.main.datasources.user.dao.UserDao;
import com.purebros.care.customer.main.datasources.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Optional;


@Component
@RestController()
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;

    @ResponseBody
    @RequestMapping(value = "/{name}/{password}", method = RequestMethod.GET)
    public Optional<User> user(@PathVariable("name") String userName, @PathVariable("password") String password){
        UserDao userDao =  new UserDao();
        userDao.setDataSource(userDataSource);
        System.out.println(userDao.findUser(userName, password));
        return userDao.findUser(userName, password);
    }
}
