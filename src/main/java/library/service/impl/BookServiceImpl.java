package library.service.impl;

import java.util.List;
import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get book by id " + id));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }
}
