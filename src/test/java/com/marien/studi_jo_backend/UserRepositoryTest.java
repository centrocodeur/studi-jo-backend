package com.marien.studi_jo_backend;

import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.enums.UserRole;
import com.marien.studi_jo_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    public void setUp(){
        // Initialisation des mocks

        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void findUserByEmail(){

        // Arrange
        User user = new User();
        user.setActivated(true);
        user.setFirstname("Tapande");
        user.setLastname("Marien");
        user.setEmail("marientaps@gmail.com");
        user.setPassword("123456");


        // Act
        Optional<User> optionalUser = userRepository.findFirstByEmail("marientaps@gmail.com");
        // Assert
        assertThat(optionalUser).isNotNull();
        //assertThat(user.getLastname()).isEqualTo("Marien");

    }


    @Test
    public void getUserRole(){

        User user = new User();
        user.setActivated(true);
        user.setFirstname("Tapande");
        user.setLastname("Marien");
        user.setEmail("marientaps@gmail.com");
        user.setPassword("123456");
        user.setRole(UserRole.CUSTOMER);

        UserRole userRole = UserRole.CUSTOMER;

         when(userRepository.findByRole(UserRole.CUSTOMER)).thenReturn(user);

    }


}
