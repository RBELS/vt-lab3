package com.adbs.vtlabs.lab3.repository.impl;

import com.adbs.vtlabs.lab3.model.entity.BookEntity;
import com.adbs.vtlabs.lab3.repository.BookRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public BookEntity save(BookEntity entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (Objects.isNull(entity.getBookId())) {
                entityManager.persist(entity);
            } else {
                entity = entityManager.merge(entity);
            }
            transaction.commit();
        }
        return entity;
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return Optional.ofNullable(entityManager.find(BookEntity.class, id));
        }
    }

    @Override
    public List<BookEntity> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            return entityManager.createQuery("select b from BookEntity as b", BookEntity.class)
                    .getResultList();
        }
    }

    @Override
    public void delete(BookEntity bookEntity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.remove(bookEntity);
        }
    }
}
