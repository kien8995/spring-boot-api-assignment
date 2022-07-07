package com.example.springbootapi.services;

import com.example.springbootapi.models.Story;
import com.example.springbootapi.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Override
    public Optional<List<Story>> findAll() {
        return Optional.of(storyRepository.findAll());
    }

    @Cacheable(value="stories", key="#id", unless="#result == null")
    @Override
    public Optional<Story> findById(long id) {
        return storyRepository.findById(id);
    }

    @Override
    public Optional<Story> saveStory(Story story) {
        return Optional.of(storyRepository.save(story));
    }

    @CachePut(value = "stories", key = "#id", unless="#result == null")
    @Override
    public void updateStory(long id, Story story) {
        Optional<Story> storyToUpdate = storyRepository.findById(id);
        if (storyToUpdate.isPresent()) {
            storyToUpdate.get().setStoryName(story.getStoryName());
            storyRepository.save(storyToUpdate.get());
        }
    }

    @CacheEvict(value = "stories", allEntries=true)
    @Override
    public void deleteStory(long id) {
        Optional<Story> storyToDelete = storyRepository.findById(id);
        storyToDelete.ifPresent(storyRepository::delete);
    }
}
