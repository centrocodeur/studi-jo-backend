package com.marien.studi_jo_backend.controller;


import com.marien.studi_jo_backend.dto.AuthenticationRequest;
import com.marien.studi_jo_backend.dto.SignupRequest;
import com.marien.studi_jo_backend.dto.UserDto;
import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.repository.UserRepository;
import com.marien.studi_jo_backend.services.auth.AuthService;
import com.marien.studi_jo_backend.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@Tag(name = "authentication controller", description = "Creation de compte utilisateur")
public class AuthController {



    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer " ;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository userRepository, JwtUtil jwtUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }




    @PostMapping("/authenticate")
    @Operation(summary = "authentication", description = "connexion de l'utisateur à l'aide de mail et de mot de passe")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));

        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );

            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Allow-Headers","Authorization, X-PING-OTHER, Origin, "+
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX+ jwt);
        }

    }


    @PostMapping("sign-up")
    @Operation(summary = "Inscription")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){

        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }



    @PostMapping("activation")
    @Operation(summary = "Activation de code")
    public ResponseEntity<?> activateUser(@RequestBody Map<String, String> activation){
        this.authService.accountActivation(activation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
