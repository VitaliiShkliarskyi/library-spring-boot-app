package library.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import library.dto.mapper.RequestDtoMapper;
import library.dto.mapper.ResponseDtoMapper;
import library.dto.request.BookRequestDto;
import library.dto.response.BookResponseDto;
import library.model.Book;
import library.service.BookService;
import library.util.SortingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final SortingService sortingService;
    private final RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper;
    private final ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper;

    public BookController(BookService bookService,
                          SortingService sortingService,
                          RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper,
                          ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper) {
        this.bookService = bookService;
        this.sortingService = sortingService;
        this.bookRequestDtoMapper = bookRequestDtoMapper;
        this.bookResponseDtoMapper = bookResponseDtoMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get all books")
    public List<BookResponseDto> getAll() {
        return bookService.getAll().stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**http://localhost:8080/users/sort?page=0&size=20&sortBy=title:ASC;author:ASC**/
    @GetMapping("/sort")
    @ApiOperation(value = "Get all sorted books page by page")
    public List<BookResponseDto> getAllSorted(@RequestParam(defaultValue = "20")
                                              @ApiParam(value =
                                                      "Default number of objects on the page is 20")
                                              Integer size,
                                              @RequestParam(defaultValue = "0")
                                              @ApiParam(value = "The first (0) page is default")
                                              Integer page,
                                              @RequestParam(defaultValue = "title")
                                              @ApiParam(value = "Default sort field is 'title'")
                                              String sortBy) {
        Sort sort = sortingService.create(page, size, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return bookService.getAll(pageRequest).stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get book by id")
    public BookResponseDto get(@PathVariable Long id) {
        return bookResponseDtoMapper.mapToDto(bookService.get(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a book")
    public BookResponseDto createUser(@RequestBody BookRequestDto bookRequestDto) {
        Book book = bookService.add(bookRequestDtoMapper.mapToModel(bookRequestDto));
        return bookResponseDtoMapper.mapToDto(book);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update information about book")
    public BookResponseDto update(@PathVariable Long id,
                                  @RequestBody BookRequestDto bookRequestDto) {
        Book book = bookRequestDtoMapper.mapToModel(bookRequestDto);
        book.setId(id);
        return bookResponseDtoMapper.mapToDto(bookService.update(book));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete book by id")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PostMapping("/take/{bookId}/user/{userId}")
    @ApiOperation(value = "Take a certain book by user id")
    public BookResponseDto takeBook(@PathVariable Long bookId, @PathVariable Long userId) {
        return bookResponseDtoMapper.mapToDto(bookService.takeBook(bookId, userId));
    }

    @PostMapping("/return/{id}")
    @ApiOperation(value = "Return book by id")
    public BookResponseDto returnBook(@PathVariable Long id) {
        return bookResponseDtoMapper.mapToDto(bookService.returnBook(id));
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get book by user id")
    public List<BookResponseDto> getBookByUserId(@PathVariable Long id) {
        return bookService.getBookByUserId(id).stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/by-number")
    @ApiOperation(value = "Get book by user phone number")
    public List<BookResponseDto> getBookByUserPhoneNumber(@RequestParam String number) {
        return bookService.getBookByUserPhoneNumber(number).stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
