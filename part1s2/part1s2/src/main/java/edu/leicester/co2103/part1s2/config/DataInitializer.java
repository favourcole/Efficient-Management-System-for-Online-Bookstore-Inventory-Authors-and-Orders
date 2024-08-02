package edu.leicester.co2103.part1s2.config;

import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.domain.OrderDomain;
import edu.leicester.co2103.part1s2.repo.AuthorRepository;
import edu.leicester.co2103.part1s2.repo.BookRepository;
import edu.leicester.co2103.part1s2.repo.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(BookRepository bookRepository, AuthorRepository authorRepository, OrderRepository orderRepository) {
        return args -> {
            Author author1 = authorRepository.save(new Author("JK Rowling", 1965, "UK"));
            Author author2 = authorRepository.save(new Author("Robert Green", 1959, "USA"));

            Book book1 = new Book("9788831000154", "Harry Potter and the Chamber of Secrets", 1998, 14.39);
            Book book2 = new Book("9780747532743", "Harry Potter and the Philosophers Stone", 1997, 12.23);
            Book book3 = new Book("9781861972781", "48 Laws Of Power", 1998, 16.99);

            OrderDomain order1 = orderRepository.save(new OrderDomain(LocalDateTime.now(), "Cory Brown"));
            OrderDomain order2 = orderRepository.save(new OrderDomain(LocalDateTime.now(), "Fadil Raji"));


            book1.getAuthors().add(author1);
            book2.getAuthors().add(author1);
            book3.getAuthors().add(author2);


            bookRepository.saveAll(Arrays.asList(book1, book2, book3));

            order1.getBooks().add(book1);
            order1.getBooks().add(book2);
            order2.getBooks().add(book3);

            orderRepository.saveAll(Arrays.asList(order1, order2));
        };
    }

}




