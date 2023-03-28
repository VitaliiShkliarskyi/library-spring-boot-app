package library.service;

import java.util.List;
import library.model.Book;

public interface BookService {
    Book add(Book book);

    List<Book> getAll();

    Book get(Long id);

    void delete(Long id);

    Book update(Book book);
}
