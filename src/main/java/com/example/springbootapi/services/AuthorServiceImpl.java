package com.example.springbootapi.services;

import com.example.springbootapi.models.Author;
import com.example.springbootapi.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Optional<List<Author>> findAll() {
        return Optional.of(authorRepository.findAll());
    }

    @Cacheable(value="authors", key="#id", unless="#result == null")
    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> saveAuthor(Author author) {
        return Optional.of(authorRepository.save(author));
    }

    @CachePut(value = "authors", key = "#id", unless="#result == null")
    @Override
    public void updateAuthor(long id, Author author) {
        Optional<Author> authorToUpdate = authorRepository.findById(id);
        if (authorToUpdate.isPresent()) {
            authorToUpdate.get().setAuthorName(author.getAuthorName());
            authorToUpdate.get().setPhoneNumber(author.getPhoneNumber());
            authorRepository.save(authorToUpdate.get());
        }
    }

    @CacheEvict(value = "authors", allEntries=true)
    @Override
    public void deleteAuthor(long id) {
        Optional<Author> authorToDelete = authorRepository.findById(id);
        authorToDelete.ifPresent(authorRepository::delete);
    }
}
