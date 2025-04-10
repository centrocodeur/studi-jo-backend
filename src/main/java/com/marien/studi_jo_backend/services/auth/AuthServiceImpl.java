package com.marien.studi_jo_backend.services.auth;

import com.marien.studi_jo_backend.dto.SignupRequest;
import com.marien.studi_jo_backend.dto.UserDto;
import com.marien.studi_jo_backend.entity.Order;
import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.entity.Validation;
import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.enums.UserRole;
import com.marien.studi_jo_backend.repository.OrderRepository;
import com.marien.studi_jo_backend.repository.UserRepository;
import com.marien.studi_jo_backend.services.validation.ValidationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ValidationService validationService;


    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstname());
        user.setLastname(signupRequest.getLastname());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setUserTrackingId(UUID.randomUUID());

        User createdUser = userRepository.save(user);

        this.validationService.recordCustomer(user);

        Order order= new Order();
        order.setAmount(0l);
        order.setTotalAmount(0l);
        order.setDiscount(0l);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.DISPONIBLE);
        orderRepository.save(order);

        UserDto userDto= new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public void accountActivation(Map<String, String> activation) {
        Validation validation = this.validationService.readCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())) {
            // throw new RuntimeException("Votre code d'activation a expiré");

            User userActivated = this.userRepository.findById(validation.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("compte introuvable"));
            this.validationService.recordCustomer(userActivated);


        } else {

            User userActivated = this.userRepository.findById(validation.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("compte introuvable"));

            userActivated.setActivated(true);

            this.userRepository.save(userActivated);

        }

    }



    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public boolean hasUserAccountIsActivated(User user) {

        boolean activatedValue = user.isActivated();
        return activatedValue;
    }


    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null==adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setRole(UserRole.ADMIN);
            user.setActivated(true);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}