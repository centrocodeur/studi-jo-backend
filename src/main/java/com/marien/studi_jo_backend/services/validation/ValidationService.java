package com.marien.studi_jo_backend.services.validation;

import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.entity.Validation;

public interface ValidationService {

    void recordCustomer(User user);

    Validation readCode(String code);
}
