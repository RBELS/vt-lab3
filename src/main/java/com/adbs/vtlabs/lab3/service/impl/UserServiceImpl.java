package com.adbs.vtlabs.lab3.service.impl;

import com.adbs.vtlabs.lab3.exception.ErrorCode;
import com.adbs.vtlabs.lab3.exception.ServiceException;
import com.adbs.vtlabs.lab3.model.service.User;
import com.adbs.vtlabs.lab3.service.AuthorityService;
import com.adbs.vtlabs.lab3.service.UserService;
import com.adbs.vtlabs.lab3.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final AuthorityService authorityService;

    public User registerUser(String username, String password) {
        userStorage.findByUsername(username).ifPresent(user -> {
            throw new ServiceException(ErrorCode.AUTH_ERROR);
        });
        return userStorage.save(new User()
                .setUsername(username)
                .setHash(authorityService.generateUserHash(username, password))
        );
    }

    public User loginUser(String username, String password) {
        return userStorage.findByUsernameAndHash(username, authorityService.generateUserHash(username, password))
                .orElseThrow(() -> new ServiceException(ErrorCode.AUTH_ERROR));
    }
}
