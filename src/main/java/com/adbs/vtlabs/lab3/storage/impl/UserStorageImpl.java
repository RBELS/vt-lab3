package com.adbs.vtlabs.lab3.storage.impl;

import com.adbs.vtlabs.lab3.model.entity.UserEntity;
import com.adbs.vtlabs.lab3.model.service.User;
import com.adbs.vtlabs.lab3.repository.UserRepository;
import com.adbs.vtlabs.lab3.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User save(User user) {
        return modelMapper.map(
                userRepository.save(modelMapper.map(user, UserEntity.class)),
                User.class
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(entity -> modelMapper.map(entity, User.class));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, User.class))
                .toList();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(modelMapper.map(user, UserEntity.class));
    }

    @Override
    public Optional<User> findByUsernameAndHash(String username, String hash) {
        return userRepository.findByUsernameAndHash(username, hash)
                .map(entity -> modelMapper.map(entity, User.class));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> modelMapper.map(entity, User.class));
    }
}
