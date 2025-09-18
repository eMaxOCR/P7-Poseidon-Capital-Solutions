package serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1);
        user.setFullname("Test User");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");
    }

    @Test
    public void getCurrentUserTest() {
        // ARRANGE
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // ACT
        User currentUser = userService.getCurrentUser();

        // ASSERT
        assertNotNull(currentUser);
        assertEquals("testuser", currentUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void getCurrentUserNotFoundTest() {
        // ARRANGE
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn("nonexistentuser");
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.getCurrentUser();
        });

        String expectedMessage = "Utilisateur non trouvé dans la base de données pour l'email: nonexistentuser";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllTest() {
        // ARRANGE
        User user2 = new User();
        user2.setId(2);
        List<User> userList = Arrays.asList(user, user2);

        when(userRepository.findAll()).thenReturn(userList);

        // ACT
        List<User> foundUsers = userService.findAll();

        // ASSERT
        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() {
        // ARRANGE
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // ACT
        Optional<User> foundUser = userService.findById(1);

        // ASSERT
        assertTrue(foundUser.isPresent());
        assertEquals(1, foundUser.get().getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void saveTest() {
        // ARRANGE
        when(userRepository.save(any(User.class))).thenReturn(user);

        // ACT
        User savedUser = userService.save(user);

        // ASSERT
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getId());
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    public void validateTest() {
    	// ARRANGE
    	when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
    	when(userRepository.save(any(User.class))).thenReturn(user);
    	
    	// ACT
    	User validatedUser = userService.validate(user);
    	
    	// ASSERT
    	assertNotNull(validatedUser);
    	assertEquals("encodedPassword", validatedUser.getPassword());
    	assertEquals("USER", validatedUser.getRole());
    	verify(userRepository, times(1)).save(validatedUser);
    }
    
    @Test
    public void updateTest() {
    	// ARRANGE
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFullname("Updated Name");
        updatedUser.setUsername("updatedusername");
        updatedUser.setPassword("newPassword");
        updatedUser.setRole("ADMIN");
        
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // ACT
        User result = userService.update(updatedUser);
        
        // ASSERT
        assertNotNull(result);
        assertEquals("Updated Name", result.getFullname());
        assertEquals("encodedNewPassword", result.getPassword());
        assertEquals("ADMIN", result.getRole());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    public void deleteUserTest() {
    	// ARRANGE
    	doNothing().when(userRepository).delete(any(User.class));
    	
    	// ACT
    	userService.deleteUser(user);
    	
    	// ASSERT
    	verify(userRepository, times(1)).delete(user);
    }
}