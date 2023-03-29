package library.service;

import java.util.List;
import library.model.Book;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book add(Book book);

    List<Book> getAll();

    List<Book> getAll(Pageable pageable);

    Book get(Long id);

    void delete(Long id);

    Book update(Book book);

    Book takeBook(Long bookId, Long userId);

    Book returnBook(Long id);

    List<Book> getAllByUserId(Long id);

    List<Book> getAllByUserPhoneNumber(String number);

    List<Book> getAllAvailableBooks();
}
