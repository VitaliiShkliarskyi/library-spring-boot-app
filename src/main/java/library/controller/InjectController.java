package library.controller;

import java.util.List;
import library.model.Book;
import library.model.User;
import library.service.BookService;
import library.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final UserService userService;
    private final BookService bookService;

    public InjectController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public String inject() {
        User firstUser = new User();
        firstUser.setName("Bob");
        firstUser.setPhoneNumber("+380920001122");
        userService.add(firstUser);
        User secondUser = new User();
        secondUser.setName("Alice");
        secondUser.setPhoneNumber("+380910001133");
        userService.add(secondUser);
        User thirdUser = new User();
        thirdUser.setName("John");
        thirdUser.setPhoneNumber("+380940002133");
        userService.add(thirdUser);
        Book firstBook = new Book();
        firstBook.setTitle("The 48 Laws of Power");
        firstBook.setAuthor("Robert Greene");
        firstBook.setUser(firstUser);
        firstBook.setTaken(true);
        bookService.add(firstBook);
        Book secondBook = new Book();
        secondBook.setTitle("Atomic Habits");
        secondBook.setAuthor("James Clear");
        secondBook.setUser(firstUser);
        secondBook.setTaken(true);
        bookService.add(secondBook);
        Book thirdBook = new Book();
        thirdBook.setTitle("The Creative Act: A Way of Being");
        thirdBook.setAuthor("Rick Rubin");
        bookService.add(thirdBook);

        return "Done!";
    }
}
