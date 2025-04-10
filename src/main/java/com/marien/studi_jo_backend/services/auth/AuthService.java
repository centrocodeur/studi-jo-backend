package com.marien.studi_jo_backend.services.auth;

import com.marien.studi_jo_backend.dto.SignupRequest;
import com.marien.studi_jo_backend.dto.UserDto;
import com.marien.studi_jo_backend.entity.User;

import java.util.Map;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

    boolean hasUserAccountIsActivated(User user);

    void accountActivation(Map<String, String> activation);
}
