package com.example.springbootapi.services;

import com.example.springbootapi.models.Story;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface StoryService {
    Optional<List<Story>> findAll();
    Optional<Story> findById(long id);
    Optional<Story> saveStory(Story Story);
    void updateStory(long id, Story Story);
    void deleteStory(long id);
}
