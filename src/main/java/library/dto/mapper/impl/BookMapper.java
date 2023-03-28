package library.dto.mapper.impl;

import library.dto.mapper.RequestDtoMapper;
import library.dto.mapper.ResponseDtoMapper;
import library.dto.request.BookRequestDto;
import library.dto.response.BookResponseDto;
import library.model.Book;
import library.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements RequestDtoMapper<BookRequestDto, Book>,
        ResponseDtoMapper<BookResponseDto, Book> {
    private final UserService userService;

    public BookMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Book mapToModel(BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setUser(userService.get(dto.getUserId()));
        return book;
    }

    @Override
    public BookResponseDto mapToDto(Book book) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setUserId(book.getUser().getId());
        return bookResponseDto;
    }
}
