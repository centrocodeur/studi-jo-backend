package com.marien.studi_jo_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AuthenticationRequest {

    private String username;

    private String password;

}