package com.example.springbootapi.controllers;

import com.example.springbootapi.loggers.MessageLogger;
import com.example.springbootapi.models.Story;
import com.example.springbootapi.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    @Autowired
    MessageLogger messageLogger;

    @Autowired
    private StoryService storyService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Story>> getStories() {
        messageLogger.info("Get all stories");
        return storyService.findAll().map(ResponseEntity::ok).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Story> getStory(@PathVariable long id) {
        messageLogger.info("Get story with id: " + id);
        return storyService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Story> saveStory(@RequestBody Story story) {
        try {
            var response = storyService.saveStory(story).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            messageLogger.info("Save story: " + story.toString());
            return response;
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable("id") long id, @RequestBody Story story) {
        try {
            storyService.updateStory(id, story);
            messageLogger.info("Update story with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Story> deleteStory(@PathVariable("id") long id) {
        try {
            storyService.deleteStory(id);
            messageLogger.info("Delete story with id: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messageLogger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
