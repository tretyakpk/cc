package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.User;

interface UserService {

    User findUser(String userName, String password);

}
