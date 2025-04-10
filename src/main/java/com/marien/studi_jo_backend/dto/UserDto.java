package com.marien.studi_jo_backend.dto;

import com.marien.studi_jo_backend.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    //private String name;

    private UserRole userRole;

    private UUID userTrackingId;

    private boolean activated= false;


}
