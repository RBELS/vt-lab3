package com.adbs.vtlabs.lab3.service;

import com.adbs.vtlabs.lab3.model.service.User;

public interface UserService {
    User registerUser(String username, String password);
    User loginUser(String username, String password);
}
