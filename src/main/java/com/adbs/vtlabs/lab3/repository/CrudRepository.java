package com.adbs.vtlabs.lab3.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T t);
    Optional<T> findById(Long id);
    List<T> findAll();
    void delete(T t);
}
