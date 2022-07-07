package com.example.springbootapi.services;

import com.example.springbootapi.models.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BookService {
    Optional<List<Book>> findAll();
    Optional<Book> findById(long id);
    Optional<Book> saveBook(Book book);
    void updateBook(long id, Book book);
    void deleteBook(long id);
}
