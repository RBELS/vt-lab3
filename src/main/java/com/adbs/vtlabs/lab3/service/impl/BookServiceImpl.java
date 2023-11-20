package com.adbs.vtlabs.lab3.service.impl;

import com.adbs.vtlabs.lab3.exception.ServiceException;
import com.adbs.vtlabs.lab3.model.service.Book;
import com.adbs.vtlabs.lab3.service.BookService;
import com.adbs.vtlabs.lab3.storage.BookStorage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.adbs.vtlabs.lab3.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookStorage bookStorage;
    private final ModelMapper modelMapper;

    @Override
    public List<Book> getAllBooks() {
        return bookStorage.findAll();
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookStorage.findById(bookId)
                .orElseThrow(() -> new ServiceException(BOOK_NOT_FOUND));
    }

    @Override
    public Book saveBook(Book book) {
        return bookStorage.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookStorage.delete(book);
    }
}
