package com.adbs.vtlabs.lab3.repository;

import com.adbs.vtlabs.lab3.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity> {
    Optional<UserEntity> findByUsernameAndHash(String username, String hash);
    Optional<UserEntity> findByUsername(String username);
}
