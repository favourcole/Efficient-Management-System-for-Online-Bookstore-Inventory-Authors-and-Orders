package edu.leicester.co2103.part1s2.repo;

import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.domain.OrderDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.leicester.co2103.part1s2.domain.OrderInput;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDomain, Long> {
    List<OrderDomain> findByCustomerName(String customerName);


    List<OrderDomain> findByBooksContaining(Book book);


}
