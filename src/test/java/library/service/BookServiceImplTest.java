package library.service;

import java.util.ArrayList;
import java.util.Optional;
import library.model.Book;
import library.model.User;
import library.repository.BookRepository;
import library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void takeBook_Ok() {
        Optional<Book> takenBook = Optional.of(createBook());
        Optional<User> takingUser = Optional.of(createUser());
        Mockito.when(bookRepository.findById(4L)).thenReturn(takenBook);
        Mockito.when(userRepository.findById(2L)).thenReturn(takingUser);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(takenBook.get());
        Book actual = bookService.takeBook(4L, 2L);
        Assertions.assertTrue(actual.isTaken());
        Assertions.assertEquals(takingUser.get(), actual.getUser());
    }

    @Test
    void takeTakenBook_notOk() {
        Book takenBook = createBook();
        takenBook.setTaken(true);
        Mockito.when(bookRepository.findById(4L)).thenReturn(Optional.of(takenBook));
        Assertions.assertThrows(RuntimeException.class, () -> bookService.takeBook(4L, 2L));
    }

    @Test
    void returnBook_Ok() {
        Book takenBook = createBook();
        User takingUser = createUser();
        takenBook.setTaken(true);
        takenBook.setUser(takingUser);
        Mockito.when(bookRepository.findById(4L)).thenReturn(Optional.of(takenBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(takenBook);
        Book actual = bookService.returnBook(4L);
        Assertions.assertFalse(actual.isTaken());
        Assertions.assertNull(actual.getUser());
    }

    @Test
    void returnFreeBook_NotOk() {
        Book takenBook = createBook();
        Mockito.when(bookRepository.findById(4L)).thenReturn(Optional.of(takenBook));
        Assertions.assertThrows(RuntimeException.class, () -> bookService.returnBook(4L));
    }

    private Book createBook() {
        Book book = new Book();
        book.setId(4L);
        book.setTitle("Atomic Habits");
        book.setAuthor("James Clear");
        book.setTaken(false);
        return book;
    }

    private User createUser() {
        User user = new User();
        user.setId(2L);
        user.setName("Bob");
        user.setPhoneNumber("+380992223344");
        user.setBooks(new ArrayList<>());
        return user;
    }
}
