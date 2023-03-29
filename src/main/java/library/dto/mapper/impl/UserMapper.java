package library.dto.mapper.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;
import library.dto.mapper.RequestDtoMapper;
import library.dto.mapper.ResponseDtoMapper;
import library.dto.request.UserRequestDto;
import library.dto.response.UserResponseDto;
import library.model.Book;
import library.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {

    @Override
    public User mapToModel(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBooks(new ArrayList<>());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setBookIds(user.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList()));
        return userResponseDto;
    }
}
