package com.adbs.vtlabs.lab3.storage;

import com.adbs.vtlabs.lab3.model.service.User;
import com.adbs.vtlabs.lab3.repository.CrudRepository;

import java.util.Optional;

public interface UserStorage extends CrudRepository<User> {
    Optional<User> findByUsernameAndHash(String username, String hash);

    Optional<User> findByUsername(String username);
}
