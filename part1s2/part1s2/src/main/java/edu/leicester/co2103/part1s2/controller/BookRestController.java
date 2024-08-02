package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.ErrorInfo;
import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.domain.OrderDomain;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.leicester.co2103.part1s2.repo.BookRepository;
import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.repo.OrderRepository;
import edu.leicester.co2103.part1s2.repo.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository, OrderRepository orderRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.authorRepository = authorRepository;

    }

    private ResponseEntity<ErrorInfo> getErrorResponse(String message, HttpStatus status) {
        ErrorInfo errorInfo = new ErrorInfo(message);
        return new ResponseEntity<>(errorInfo, status);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return getErrorResponse("No Books found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookByISBN(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
        } else {
            return getErrorResponse("Book not found with isbn: " + isbn, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
        if (existingBook.isPresent()) {
            return getErrorResponse("A Book named " + book.getTitle() + " already exists.", HttpStatus.CONFLICT);
        }
        Book createdBook = bookRepository.save(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setPrice(book.getPrice());
            Book updatedBook = bookRepository.save(existingBook);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return getErrorResponse("Book not found with isbn: " + isbn, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();


            List<OrderDomain> orders = orderRepository.findByBooksContaining(book);
            for (OrderDomain order : orders) {
                order.getBooks().remove(book);
                orderRepository.save(order);
            }


            bookRepository.delete(book);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return getErrorResponse("Book not found with ISBN: " + isbn, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{isbn}/authors")
    public ResponseEntity<?> getAuthorsOfBook(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            List<Author> authors = book.getAuthors();
            return ResponseEntity.ok(authors);
        } else {
            return getErrorResponse("Book not found with ISBN: " + isbn, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{isbn}/orders")
    public ResponseEntity<?> getOrdersContainingBook(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            List<OrderDomain> orders = orderRepository.findByBooksContaining(book);
            return ResponseEntity.ok(orders);
        } else {
            return getErrorResponse("Book not found with ISBN: " + isbn, HttpStatus.NOT_FOUND);
        }
    }
}
