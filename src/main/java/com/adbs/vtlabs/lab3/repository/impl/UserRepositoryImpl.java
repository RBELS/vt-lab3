package com.adbs.vtlabs.lab3.repository.impl;

import com.adbs.vtlabs.lab3.model.entity.UserEntity;
import com.adbs.vtlabs.lab3.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public UserEntity save(UserEntity entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (Objects.isNull(entity.getUserId())) {
                entityManager.persist(entity);
            } else {
                entity = entityManager.merge(entity);
            }
            transaction.commit();
        }
        return entity;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return Optional.ofNullable(entityManager.find(UserEntity.class, id));
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return entityManager.createQuery("select u from UserEntity as u", UserEntity.class)
                    .getResultList();
        }
    }

    @Override
    public void delete(UserEntity entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(
                    entityManager.merge(entity)
            );
            transaction.commit();
        }
    }

    @Override
    public Optional<UserEntity> findByUsernameAndHash(String username, String hash) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return entityManager.createQuery("select u from UserEntity as u where u.username = :username and u.hash = :hash", UserEntity.class)
                    .setParameter("username", username)
                    .setParameter("hash", hash)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return entityManager.createQuery("select u from UserEntity as u where u.username = :username", UserEntity.class)
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst();
        }
    }
}
