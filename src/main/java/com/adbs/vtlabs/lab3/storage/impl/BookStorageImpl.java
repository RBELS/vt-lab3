package com.adbs.vtlabs.lab3.storage.impl;

import com.adbs.vtlabs.lab3.model.entity.BookEntity;
import com.adbs.vtlabs.lab3.model.service.Book;
import com.adbs.vtlabs.lab3.repository.BookRepository;
import com.adbs.vtlabs.lab3.storage.BookStorage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookStorageImpl implements BookStorage {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public Book save(Book book) {
        return modelMapper.map(
                bookRepository.save(modelMapper.map(book, BookEntity.class)),
                Book.class
        );
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookEntity -> modelMapper.map(bookEntity, Book.class));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll().stream()
                .map(bookEntity -> modelMapper.map(bookEntity, Book.class))
                .toList();
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(modelMapper.map(book, BookEntity.class));
    }
}
