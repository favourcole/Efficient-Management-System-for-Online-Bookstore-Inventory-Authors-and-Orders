package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.ErrorInfo;
import edu.leicester.co2103.part1s2.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.leicester.co2103.part1s2.repo.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderRestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private ResponseEntity<ErrorInfo> getErrorResponse(String message, HttpStatus status) {
        ErrorInfo errorInfo = new ErrorInfo(message);
        return new ResponseEntity<>(errorInfo, status);
    }

    @GetMapping
    public ResponseEntity<? > getAllOrders() {
        List<OrderDomain> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return getErrorResponse("No orders found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDomain orderDomain) {
        String customerName = orderDomain.getCustomerName();


        List<OrderDomain> existingOrders = orderRepository.findByCustomerName(customerName);
        if (!existingOrders.isEmpty()) {
            return getErrorResponse("Theres an already an order for " + orderDomain.getCustomerName(), HttpStatus.CONFLICT);
        }

        OrderDomain newOrder = new OrderDomain();
        newOrder.setCustomerName(customerName);
        newOrder.setTimeStamp(LocalDateTime.now());
        OrderDomain createdOrder = orderRepository.save(newOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<OrderDomain> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return new ResponseEntity<>(optionalOrder.get(), HttpStatus.OK);
        } else {
            return getErrorResponse("Order not found with id: " + id, HttpStatus.OK);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderDomain orderDomain) {
        Optional<OrderDomain> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderDomain existingOrder = optionalOrder.get();
            existingOrder.setCustomerName(orderDomain.getCustomerName());
            OrderDomain updatedOrder = orderRepository.save(existingOrder);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return getErrorResponse("Order not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return getErrorResponse("Order not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksInOrder(@PathVariable Long id) {
        Optional<OrderDomain> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderDomain order = optionalOrder.get();
            List<Book> booksInOrder = order.getBooks();
            return new ResponseEntity<>(booksInOrder, HttpStatus.OK);
        } else {
            return getErrorResponse("Order not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/books")
    public ResponseEntity<?> addBookToOrder(@PathVariable Long id, @RequestBody Book book) {
        Optional<OrderDomain> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderDomain order = optionalOrder.get();

            Book newbook = new Book(book.getISBN(), book.getTitle(), book.getPublicationYear(), book.getPrice());

            order.addBook(newbook);

            OrderDomain updatedOrder = orderRepository.save(order);

            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return getErrorResponse("Order not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}/books/{ISBN}")
    public ResponseEntity<?> removeBookFromOrder(@PathVariable Long id, @PathVariable String ISBN) {
        Optional<OrderDomain> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderDomain order = optionalOrder.get();
            Optional<Book> optionalBook = order.getBooks().stream()
                    .filter(book -> book.getISBN().equals(ISBN))
                    .findFirst();

            if (optionalBook.isPresent()) {
                order.getBooks().remove(optionalBook.get());
                orderRepository.save(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return getErrorResponse("Book not found with ISBN: " + ISBN, HttpStatus.NOT_FOUND);
            }
        } else {
            return getErrorResponse("Order not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

}

