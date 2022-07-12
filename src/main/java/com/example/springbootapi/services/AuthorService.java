package com.example.springbootapi.services;

import com.example.springbootapi.models.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AuthorService {
    Optional<List<Author>> findAll();
    Optional<Author> findById(long id);
    Optional<Author> saveAuthor(Author author);
    void updateAuthor(long id, Author author);
    void deleteAuthor(long id);
}
