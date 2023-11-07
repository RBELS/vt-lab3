package com.adbs.vtlabs.lab3.service;

import com.adbs.vtlabs.lab3.model.service.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
    Book saveBook(Book book);
    void deleteBook(Book book);
}
