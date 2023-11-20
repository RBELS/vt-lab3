package com.adbs.vtlabs.lab3.service;

import com.adbs.vtlabs.lab3.model.service.User;

public interface AuthorityService {
    String generateUserHash(String username, String password);
    String generateUserJwt(User user);
    boolean verifyUserJwt(String token);
}
