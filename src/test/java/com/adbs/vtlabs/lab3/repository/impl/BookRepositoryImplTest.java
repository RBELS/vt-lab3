package com.adbs.vtlabs.lab3.repository.impl;

import com.adbs.vtlabs.lab3.model.entity.BookEntity;
import com.adbs.vtlabs.lab3.repository.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class BookRepositoryImplTest {

    private static EntityManagerFactory entityManagerFactory;
    private static BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lab3pu-test");
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
        bookRepository.save(new BookEntity()
                .setName("Book 1")
                .setAuthor("Author 1")
                .setDescription("Desc 1")
                .setPrice(new BigDecimal("19.99"))
        );
        bookRepository.save(new BookEntity()
                .setName("Book 2")
                .setAuthor("Author 2")
                .setDescription("Desc 1")
                .setPrice(new BigDecimal("29.99"))
        );
    }

    @Test
    void findById() {
        BookEntity bookEntity = bookRepository.findById(1L).get();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Book 1", bookEntity.getName()),
                () -> Assertions.assertEquals("Author 1", bookEntity.getAuthor()),
                () -> Assertions.assertEquals("Desc 1", bookEntity.getDescription()),
                () -> Assertions.assertEquals(new BigDecimal("19.99"), bookEntity.getPrice())
        );
    }

    @Test
    void findAll() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        BookEntity bookEntity = bookEntities.stream().filter(entity -> entity.getBookId().equals(1L)).findFirst().get();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Book 1", bookEntity.getName()),
                () -> Assertions.assertEquals("Author 1", bookEntity.getAuthor()),
                () -> Assertions.assertEquals("Desc 1", bookEntity.getDescription()),
                () -> Assertions.assertEquals(new BigDecimal("19.99"), bookEntity.getPrice())
        );
    }

    @Test
    void delete() {
        Optional<BookEntity> before = bookRepository.findById(2L);
        Assertions.assertTrue(before.isPresent());
        bookRepository.delete(before.get());
        Optional<BookEntity> after = bookRepository.findById(2L);
        Assertions.assertFalse(after.isPresent());
    }
}