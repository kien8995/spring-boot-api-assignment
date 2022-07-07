package com.example.springbootapi;

import com.example.springbootapi.controllers.BookController;
import com.example.springbootapi.controllers.StoryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootApiApplicationTests {
    @Autowired
    private BookController bookController;

    @Autowired
    private StoryController storyController;

    @Test
    void contextLoads() {
        assertThat(bookController).isNotNull();
        assertThat(storyController).isNotNull();
    }
}
