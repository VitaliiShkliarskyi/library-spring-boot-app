package library.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import library.dto.mapper.RequestDtoMapper;
import library.dto.mapper.ResponseDtoMapper;
import library.dto.request.UserRequestDto;
import library.dto.response.UserResponseDto;
import library.model.User;
import library.service.UserService;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SortingService sortingService;
    private final RequestDtoMapper<UserRequestDto, User> userRequestDtoMapper;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseDtoMapper;


    public UserController(UserService userService,
                          SortingService sortingService,
                          RequestDtoMapper<UserRequestDto, User> userRequestDtoMapper,
                          ResponseDtoMapper<UserResponseDto, User> userResponseDtoMapper) {
        this.userService = userService;
        this.sortingService = sortingService;
        this.userRequestDtoMapper = userRequestDtoMapper;
        this.userResponseDtoMapper = userResponseDtoMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get all users")
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(userResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
/** http://localhost:8080/users/sort?page=0&size=20&sortBy=name:ASC;phoneNumber:DESC **/
    @GetMapping("/sort")
    @ApiOperation(value = "Get all sorted users by page")
    public List<UserResponseDto> getAllSorted(@RequestParam(defaultValue = "20")
                                            @ApiParam(value =
                                                    "Default number of objects on the page is 20")
                                             Integer size,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "The first (0) page is default")
                                            Integer page,
                                            @RequestParam(defaultValue = "name")
                                            @ApiParam(value = "Default sort field is 'name'")
                                            String sortBy) {
        Sort sort = sortingService.create(page, size, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userService.getAll(pageRequest).stream()
                .map(userResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id")
    public UserResponseDto get(@PathVariable Long id) {
        return userResponseDtoMapper.mapToDto(userService.get(id));
    }

    @PostMapping
    @ApiOperation(value = "Create user")
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = userService.add(userRequestDtoMapper.mapToModel(userRequestDto));
        return userResponseDtoMapper.mapToDto(user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update information about user")
    public UserResponseDto update(@PathVariable Long id,
                                     @RequestBody UserRequestDto userRequestDto) {
        User user = userRequestDtoMapper.mapToModel(userRequestDto);
        user.setId(id);
        return userResponseDtoMapper.mapToDto(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user by ID")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
