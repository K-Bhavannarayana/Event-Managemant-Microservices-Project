package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;

@SpringBootTest
class UserManagementApplicationTests {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDetails sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new UserDetails();
        sampleUser.setUserId(2000); // User ID starts from 2000
        sampleUser.setUserName("John Doe");
        sampleUser.setUserEmail("johndoe@example.com");
    }

    @Test
    void testAddUser_Success() {
        when(repository.save(sampleUser)).thenReturn(sampleUser);

        String result = userService.addUser(sampleUser);
        assertEquals("User Details Saved Successfully", result);

        verify(repository, times(1)).save(sampleUser);
    }

    @Test
    void testAddUser_Error() {
        when(repository.save(sampleUser)).thenReturn(null);

        String result = userService.addUser(sampleUser);
        assertEquals("Error saving the user", result);
    }

    @Test
    void testUpdateUser_Success() {
        when(repository.save(sampleUser)).thenReturn(sampleUser);

        String result = userService.updateUser(sampleUser);
        assertEquals("User Details Updated Successfully", result);

        verify(repository, times(1)).save(sampleUser);
    }

    @Test
    void testUpdateUser_Error() {
        when(repository.save(sampleUser)).thenReturn(null);

        String result = userService.updateUser(sampleUser);
        assertEquals("Error Updating the user", result);
    }

    @Test
    void testGetUserDetails_Found() throws UserNotFoundException {
        when(repository.findById(2000)).thenReturn(Optional.of(sampleUser));

        UserDetails result = userService.getUserDetails(2000);
        assertNotNull(result);
        assertEquals("John Doe", result.getUserName());

        verify(repository, times(1)).findById(2000);
    }

    @Test
    void testGetUserDetails_NotFound() {
        when(repository.findById(2000)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(2000));

        verify(repository, times(1)).findById(2000);
    }

    @Test
    void testRemoveUser() {
        doNothing().when(repository).deleteById(2000);

        String result = userService.removeUser(2000);
        assertEquals("User Details deleted", result);

        verify(repository, times(1)).deleteById(2000);
    }

    @Test
    void testGetUserPresence_UserExists() {
        when(repository.findById(2000)).thenReturn(Optional.of(sampleUser));

        assertTrue(userService.getUserPresence(2000));
    }

    @Test
    void testGetUserPresence_UserNotFound() {
        when(repository.findById(2000)).thenReturn(Optional.empty());

        assertFalse(userService.getUserPresence(2000));
    }
}
