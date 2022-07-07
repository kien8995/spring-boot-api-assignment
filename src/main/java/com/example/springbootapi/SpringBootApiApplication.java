package com.example.springbootapi;

import com.example.springbootapi.models.Book;
import com.example.springbootapi.models.Story;
import com.example.springbootapi.repositories.BookRepository;
import com.example.springbootapi.repositories.StoryRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.HashSet;
import java.util.Set;


@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Book API", version = "1.0", description = "Books Information"))
public class SpringBootApiApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StoryRepository storyRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        Story story1 = new Story();
        story1.setStoryName(faker.harryPotter().quote());
        Story story2 = new Story();
        story2.setStoryName(faker.harryPotter().quote());
        Story story3 = new Story();
        story3.setStoryName(faker.harryPotter().quote());
        Story story4 = new Story();
        story4.setStoryName(faker.harryPotter().quote());

        Book book1 = new Book();
        book1.setBookName(faker.book().title());
        Book book2 = new Book();
        book2.setBookName(faker.book().title());

        Set<Story> stories1 = new HashSet<>(Set.of(story1, story2));
        Set<Story> stories2 = new HashSet<>(Set.of(story3, story4));
        book1.setStoryList(stories1);
        book2.setStoryList(stories2);

        story1.setBook(book1);
        story2.setBook(book1);
        story3.setBook(book2);
        story4.setBook(book2);

        bookRepository.saveAll(Set.of(book1, book2));
        storyRepository.saveAll(Set.of(story1, story2, story3, story4));
    }
}
