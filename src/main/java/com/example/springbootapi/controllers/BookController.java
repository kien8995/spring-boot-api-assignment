package com.example.springbootapi.controllers;

import com.example.springbootapi.loggers.MessageLogger;
import com.example.springbootapi.models.Book;
import com.example.springbootapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    MessageLogger messageLogger;

    @Autowired
    private BookService bookService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Book>> getBooks() {
        messageLogger.info("Get all books");
        return bookService.findAll().map(ResponseEntity::ok).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        messageLogger.info("Get book with id: " + id);
        return bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            var response = bookService.saveBook(book).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            messageLogger.info("Save book: " + book.toString());
            return response;
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        try {
            bookService.updateBook(id, book);
            messageLogger.info("Update book with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        try {
            bookService.deleteBook(id);
            messageLogger.info("Delete book with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
