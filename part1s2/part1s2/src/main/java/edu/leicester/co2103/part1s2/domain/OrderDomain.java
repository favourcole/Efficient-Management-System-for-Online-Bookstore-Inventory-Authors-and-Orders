package edu.leicester.co2103.part1s2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"books"})
public class OrderDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeStamp;
    private String customerName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_isbn")
    )
    private List<Book> books = new ArrayList<>();


    public OrderDomain(LocalDateTime timeStamp, String customerName) {
        this.timeStamp = timeStamp;
        this.customerName = customerName;
    }

    public OrderDomain() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.getOrders().add(this);
        }
    }

    @Override
    public String toString() {
        return "OrderDomain{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", customerName='" + customerName + '\'' +
                ", books=" + books +
                '}';
    }
}

