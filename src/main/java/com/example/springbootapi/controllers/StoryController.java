package com.example.springbootapi.controllers;

import com.example.springbootapi.models.Story;
import com.example.springbootapi.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    Logger logger = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    private StoryService storyService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Story>> getStories() {
        return storyService.findAll().map(ResponseEntity::ok).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Story> getStory(@PathVariable long id) {
        return storyService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Story> saveStory(@RequestBody Story story) {
        try {
            return storyService.saveStory(story).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable("id") long id, @RequestBody Story story) {
        try {
            storyService.updateStory(id, story);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Story> deleteStory(@PathVariable("id") long id) {
        try {
            storyService.deleteStory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
