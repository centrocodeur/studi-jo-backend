package com.marien.studi_jo_backend;

import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void signupTest(){

        // Arrange
        User user = new User();
        user.setActivated(true);
        user.setFirstname("Tapande");
        user.setLastname("Marien");
        user.setEmail("marientaps@gmail.com");
        user.setPassword("12334445");

        // Act
        User savedUser= userRepository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getLastname()).isEqualTo("Marien");

    }




    @Test
    public void getUserList(){
        Iterable<User> users= userRepository.findAll();

        assertThat(users).hasSizeGreaterThan(0);

        for (User user: users){
            System.out.println(user.getLastname());
        }
    }

    @Test
    public void findUserByEmail(){

        String email = "marientaps@gmail.com";

        Optional<User> optionalUser = userRepository.findFirstByEmail(email);


        assertThat(optionalUser).isPresent();

    }

    @Test
    public void getUserTrackingId(){
        String email = "marientaps@gmail.com";

        Optional<User> optionalUser = userRepository.findFirstByEmail(email);


        assertThat(optionalUser).isPresent();


        System.out.println("User: "+ optionalUser.get().getUserTrackingId());
    }


}
