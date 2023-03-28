package library.service.impl;

import java.util.List;
import library.model.Book;
import library.model.User;
import library.repository.BookRepository;
import library.repository.UserRepository;
import library.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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
    public List<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).toList();
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

    @Override
    public Book takeBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new RuntimeException("Can't get book by id " + bookId));
        if (book.isTaken()) {
            throw new RuntimeException("Book by id " + bookId + " is already taken");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Can't get user by id " + bookId));
        book.setUser(user);
        book.setTaken(true);
        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get book by id " + id));
        if (!book.isTaken()) {
            throw new RuntimeException("Book by id " + id + " is free");
        }
        book.setUser(null);
        book.setTaken(false);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBookByUserId(Long id) {
        return bookRepository.findAllByUserId(id);
    }

    @Override
    public List<Book> getBookByUserPhoneNumber(String number) {
        return userRepository.findByPhoneNumber(number).getBooks();
    }
}
