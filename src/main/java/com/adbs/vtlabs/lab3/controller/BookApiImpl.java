package com.adbs.vtlabs.lab3.controller;

import com.adbs.vtlabs.api.books.controllers.BookApi;
import com.adbs.vtlabs.api.books.controllers.models.Book;
import com.adbs.vtlabs.api.books.controllers.models.BookExtended;
import com.adbs.vtlabs.lab3.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookApiImpl implements BookApi {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<BookExtended>> booksGet() {
        return ResponseEntity.ok(bookService.getAllBooks().stream()
                .map(book -> modelMapper.map(book, BookExtended.class))
                .toList());
    }

    @Override
    public ResponseEntity<BookExtended> booksBookIdGet(Long bookId) {
        return ResponseEntity.ok(
                modelMapper.map(bookService.getBookById(bookId), BookExtended.class)
        );
    }

    @Override
    public ResponseEntity<BookExtended> booksPost(@Valid Book book) {
        return ResponseEntity.ok(
                modelMapper.map(bookService.saveBook(
                        modelMapper.map(book, com.adbs.vtlabs.lab3.model.service.Book.class)
                ), BookExtended.class)
        );
    }

    @Override
    public ResponseEntity<BookExtended> booksBookIdPut(Long bookId, @Valid Book book) {
        return ResponseEntity.ok(
                modelMapper.map(
                        bookService.saveBook(
                                modelMapper.map(book, com.adbs.vtlabs.lab3.model.service.Book.class).setBookId(bookId)
                        ), BookExtended.class
                )
        );
    }

    @Override
    public ResponseEntity<BookExtended> booksBookIdDelete(Long bookId) {
        var book = bookService.getBookById(bookId);
        bookService.deleteBook(book);
        return ResponseEntity.ok(modelMapper.map(book, BookExtended.class));
    }
}
