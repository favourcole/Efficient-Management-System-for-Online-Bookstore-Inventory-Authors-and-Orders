package edu.leicester.co2103.part1s2.repo;

import edu.leicester.co2103.part1s2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorsId(Long authorId);

    Optional<Book> findByISBN(String isbn);

    Optional<Book> findByTitle(String title);

    void deleteByISBN(String isbn);
}

