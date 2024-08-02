package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.domain.OrderDomain;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.repo.AuthorRepository;
import edu.leicester.co2103.part1s2.repo.BookRepository;
import edu.leicester.co2103.part1s2.repo.OrderRepository;
import edu.leicester.co2103.part1s2.ErrorInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public AuthorRestController(AuthorRepository authorRepository, BookRepository bookRepository, OrderRepository orderRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    private ResponseEntity<ErrorInfo> getErrorResponse(String message, HttpStatus status) {
        ErrorInfo errorInfo = new ErrorInfo(message);
        return new ResponseEntity<>(errorInfo, status);
    }


    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return getErrorResponse("No authors found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
        if (existingAuthor.isPresent()) {
            return getErrorResponse("An author named " + author.getName() + " already exists.", HttpStatus.CONFLICT);
        }


        Author createdAuthor = authorRepository.save(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return new ResponseEntity<>(optionalAuthor.get(), HttpStatus.OK);
        } else {
            return getErrorResponse("Author not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setName(author.getName());
            existingAuthor.setBirthYear(author.getBirthYear());
            existingAuthor.setNationality(author.getNationality());
            Author updatedAuthor = authorRepository.save(existingAuthor);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            return getErrorResponse("Author not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            List<Book> books = author.getBooks();


            for (Book book : books) {
                List<OrderDomain> orders = orderRepository.findByBooksContaining(book);


                for (OrderDomain order : orders) {
                    order.getBooks().remove(book);
                    orderRepository.save(order);
                }


                bookRepository.delete(book);
            }

            authorRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return getErrorResponse("Author not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksByAuthorId(@PathVariable Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            List<Book> books = bookRepository.findByAuthorsId(id);
            return ResponseEntity.ok(books);
        } else {
            return getErrorResponse("Author not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

}
