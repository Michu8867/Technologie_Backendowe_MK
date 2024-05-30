package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new TestExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User("Jan", "Kowalski", LocalDate.of(1990, 1, 1), "jan.kowalski@gmail.com");
        User user2 = new User("Anna", "Nowak", LocalDate.of(1985, 5, 5), "anna.nowak@gmail.com");
        List<User> users = Arrays.asList(user1, user2);
        UserDto userDto1 = new UserDto(1L, "Jan", "Kowalski", LocalDate.of(1990, 1, 1), "jan.kowalski@gmail.com", 32);
        UserDto userDto2 = new UserDto(2L, "Anna", "Nowak", LocalDate.of(1985, 5, 5), "anna.nowak@gmail.com", 37);

        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[1].firstName").value("Anna"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("Piotr", "Zalewski", LocalDate.of(2000, 10, 10), "piotr.zalewski@gmail.com");
        UserDto userDto = new UserDto(3L, "Piotr", "Zalewski", LocalDate.of(2000, 10, 10), "piotr.zalewski@gmail.com", 22);

        when(userService.createUser(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Piotr\", \"lastName\":\"Zalewski\", \"birthdate\":\"2000-10-10\", \"email\":\"piotr.zalewski@gmail.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Piotr"))
                .andExpect(jsonPath("$.lastName").value("Zalewski"))
                .andExpect(jsonPath("$.email").value("piotr.zalewski@gmail.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User("Tomasz", "Maj", LocalDate.of(1995, 2, 2), "tomasz.maj@gmail.com");
        UserDto userDto = new UserDto(1L, "Tomasz", "Maj", LocalDate.of(1995, 2, 2), "tomasz.maj@gmail.com", 27);
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setFirstName("Tomasz");
        userUpdateDto.setLastName("Maj");
        userUpdateDto.setEmail("tomasz.maj@gmail.com");
        userUpdateDto.setBirthdate(LocalDate.of(1995, 2, 2));

        when(userService.updateUser(anyLong(), any(UserUpdateDto.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Tomasz\", \"lastName\":\"Maj\", \"birthdate\":\"1995-02-02\", \"email\":\"tomasz.maj@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Tomasz"))
                .andExpect(jsonPath("$.lastName").value("Maj"))
                .andExpect(jsonPath("$.email").value("tomasz.maj@gmail.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User("Marek", "Lewandowski", LocalDate.of(1980, 3, 3), "marek.lewandowski@gmail.com");
        UserDto userDto = new UserDto(1L, "Marek", "Lewandowski", LocalDate.of(1980, 3, 3), "marek.lewandowski@gmail.com", 42);

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Marek"))
                .andExpect(jsonPath("$.lastName").value("Lewandowski"))
                .andExpect(jsonPath("$.email").value("marek.lewandowski@gmail.com"));
    }

    @Test
    public void testGetUserByEmail_NotFound() throws Exception {
        when(userService.findUsersByEmailAddress(anyString())).thenThrow(new UserNotFoundException("nonexistent@gmail.com"));

        mockMvc.perform(get("/v1/users/email")
                        .param("email", "nonexistent@gmail.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with email: nonexistent@gmail.com"));
    }

    @Test
    public void testDeleteUserById_NotFound() throws Exception {
        doThrow(new UserNotFoundException(999L)).when(userService).deleteUserById(anyLong());

        mockMvc.perform(delete("/v1/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 999"));
    }

    @Test
    public void testGetUsersOlderThan() throws Exception {
        User user1 = new User("Jan", "Kowalski", LocalDate.of(1990, 1, 1), "jan.kowalski@gmail.com");
        User user2 = new User("Anna", "Nowak", LocalDate.of(1985, 5, 5), "anna.nowak@gmail.com");
        UserDto userDto1 = new UserDto(1L, "Jan", "Kowalski", LocalDate.of(1990, 1, 1), "jan.kowalski@gmail.com", 32);
        UserDto userDto2 = new UserDto(2L, "Anna", "Nowak", LocalDate.of(1985, 5, 5), "anna.nowak@gmail.com", 37);

        when(userService.findUsersOlderThan(anyString())).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        mockMvc.perform(get("/v1/users/older/30"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[1].firstName").value("Anna"));
    }

    @ControllerAdvice
    public static class TestExceptionHandler {

        @ExceptionHandler(UserNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
            List<String> errors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(new ValidationErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }

        public static class ErrorResponse {
            private String message;

            public ErrorResponse(String message) {
                this.message = message;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }

        public static class ValidationErrorResponse {
            private List<String> errors;

            public ValidationErrorResponse(List<String> errors) {
                this.errors = errors;
            }

            public List<String> getErrors() {
                return errors;
            }

            public void setErrors(List<String> errors) {
                this.errors = errors;
            }
        }
    }
}
