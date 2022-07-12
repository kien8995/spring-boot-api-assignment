package com.example.springbootapi.controllers;

import com.example.springbootapi.loggers.MessageLogger;
import com.example.springbootapi.models.Author;
import com.example.springbootapi.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    MessageLogger messageLogger;

    @Autowired
    private AuthorService authorService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Author>> getAuthors() {
        messageLogger.info("Get all authors");
        return authorService.findAll().map(ResponseEntity::ok).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Author> getAuthor(@PathVariable long id) {
        messageLogger.info("Get author with id: " + id);
        return authorService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        try {
            var response = authorService.saveAuthor(author).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            messageLogger.info("Save author: " + author.toString());
            return response;
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        try {
            authorService.updateAuthor(id, author);
            messageLogger.info("Update author with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable("id") long id) {
        try {
            authorService.deleteAuthor(id);
            messageLogger.info("Delete author with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
