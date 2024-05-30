package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindUsersByEmailAddress() {
        String email = "jan.nowak@gmail.com";
        LocalDate birthdate = LocalDate.of(2008, 5, 5);
        User user = new User("Jan", "Nowak", birthdate, email);

        when(userRepository.findByEmailContainingIgnoreCase(email)).thenReturn(List.of(user));

        List<User> users = userService.findUsersByEmailAddress(email);
        assertEquals(1, users.size());
        assertEquals(email, users.get(0).getEmail());
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setFirstName("Piotr");
        updateDto.setLastName("Kowalski");
        updateDto.setBirthdate(LocalDate.of(1990, 1, 1));
        updateDto.setEmail("piotr.kowalski@gmail.com");

        User existingUser = new User("Marek", "Wiśniewski", LocalDate.of(1985, 5, 5), "marek.wisniewski@gmail.com");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        userService.updateUser(userId, updateDto);

        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);

        assertEquals("Piotr", existingUser.getFirstName());
        assertEquals("Kowalski", existingUser.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), existingUser.getBirthdate());
        assertEquals("piotr.kowalski@gmail.com", existingUser.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setFirstName("Piotr");
        updateDto.setLastName("Kowalski");
        updateDto.setBirthdate(LocalDate.of(1990, 1, 1));
        updateDto.setEmail("piotr.kowalski@gmail.com");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updateDto));

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }
}


