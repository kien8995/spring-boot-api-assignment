package com.example.springbootapi.services;

import com.example.springbootapi.models.Book;
import com.example.springbootapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Optional<List<Book>> findAll() {
        return Optional.of(bookRepository.findAll());
    }

    @Cacheable(value="books", key="#id", unless="#result == null")
    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> saveBook(Book book) {
        book.getStoryList().forEach(story -> story.setBook(book));
        return Optional.of(bookRepository.save(book));
    }

    @CachePut(value = "books", key = "#id", unless="#result == null")
    @Override
    public void updateBook(long id, Book book) {
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isPresent()) {
            bookToUpdate.get().setBookName(book.getBookName());
            bookRepository.save(bookToUpdate.get());
        }
    }

    @CacheEvict(value = "books", allEntries=true)
    @Override
    public void deleteBook(long id) {
        Optional<Book> bookToDelete = bookRepository.findById(id);
        bookToDelete.ifPresent(bookRepository::delete);
    }
}
