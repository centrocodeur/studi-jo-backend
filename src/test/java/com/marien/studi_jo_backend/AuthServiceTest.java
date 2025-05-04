package com.marien.studi_jo_backend;

import com.marien.studi_jo_backend.entity.Order;
import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.enums.UserRole;
import com.marien.studi_jo_backend.repository.OrderRepository;
import com.marien.studi_jo_backend.repository.UserRepository;
import com.marien.studi_jo_backend.services.auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp(){
        // Initialisation des mocks

        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void createUserTest(){

        User user = new User();
        user.setActivated(true);
        user.setFirstname("Tapande");
        user.setLastname("Marien");
        user.setEmail("marientaps@gmail.com");
        user.setPassword("123456");
        user.setRole(UserRole.CUSTOMER);
        user.setUserTrackingId(UUID.randomUUID());

        Order order= new Order();
        order.setAmount(0l);
        order.setTotalAmount(0l);
        order.setDiscount(0l);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.Pending);


        Optional<User> optionalUser = userRepository.findById(1L);
        // Assert
        assertThat(optionalUser).isNotNull();





    }





}
