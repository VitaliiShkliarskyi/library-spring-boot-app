package library.controller;

import java.util.ArrayList;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import library.dto.request.UserRequestDto;
import library.model.Book;
import library.model.User;
import library.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getAllUsers_Ok() {
        List<User> mockUsers = createMockUsers();
        Mockito.when(userService.getAll()).thenReturn(mockUsers);
        RestAssuredMockMvc.when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(3))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Bob"))
                .body("[0].phoneNumber", Matchers.equalTo("+380920001122"))
                .body("[0].bookIds", Matchers.hasItems(5, 7))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].name", Matchers.equalTo("Alice"))
                .body("[1].phoneNumber", Matchers.equalTo("+380910001133"))
                .body("[1].bookIds", Matchers.empty())
                .body("[2].id", Matchers.equalTo(3))
                .body("[2].name", Matchers.equalTo("John"))
                .body("[2].phoneNumber", Matchers.equalTo("+380940002133"))
                .body("[2].bookIds", Matchers.empty());
    }

    @Test
    public void createUser_Ok() {
        User userToSave = createUser();
        User expectedUser = createUser();
        expectedUser.setId(3L);
        Mockito.when(userService.add(userToSave)).thenReturn(expectedUser);
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName(userToSave.getName());
        requestDto.setPhoneNumber(userToSave.getPhoneNumber());
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(3))
                .body("name", Matchers.equalTo("Bob"))
                .body("phoneNumber", Matchers.equalTo("+380992223344"))
                .body("bookIds", Matchers.empty());
    }

    @Test
    public void createUserWithInvalidNumber_NotOk() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("Bob");
        requestDto.setPhoneNumber("+019");
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("timestamp", Matchers.notNullValue())
                .body("status", Matchers.equalTo(400))
                .body("errors", Matchers.hasItems("Invalid phone number"));
    }

    @Test
    public void updateUser_Ok() {
        User userToUpdate = createUser();
        userToUpdate.setId(1L);
        User expectedUser = userToUpdate;
        expectedUser.setPhoneNumber("+380991111111");
        Mockito.when(userService.update(userToUpdate)).thenReturn(expectedUser);
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName(expectedUser.getName());
        requestDto.setPhoneNumber(expectedUser.getPhoneNumber());
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .put("/users/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Bob"))
                .body("phoneNumber", Matchers.equalTo("+380991111111"))
                .body("bookIds", Matchers.empty());
    }

    @Test
    public void deleteUser_Ok() {
        Mockito.doNothing().when(userService).delete(1L);
        RestAssuredMockMvc
                .when()
                .delete("/users/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    private List<User> createMockUsers() {
        Book firstBook = new Book();
        firstBook.setId(5L);
        Book secondBook = new Book();
        secondBook.setId(7L);
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setName("Bob");
        firstUser.setPhoneNumber("+380920001122");
        firstUser.setBooks(List.of(firstBook, secondBook));
        userService.add(firstUser);
        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setName("Alice");
        secondUser.setPhoneNumber("+380910001133");
        secondUser.setBooks(new ArrayList<>());
        userService.add(secondUser);
        User thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setName("John");
        thirdUser.setPhoneNumber("+380940002133");
        thirdUser.setBooks(new ArrayList<>());
        userService.add(thirdUser);
        return List.of(firstUser, secondUser, thirdUser);
    }

    private User createUser() {
        User user = new User();
        user.setName("Bob");
        user.setPhoneNumber("+380992223344");
        user.setBooks(new ArrayList<>());
        return user;
    }
}
