package com.marien.studi_jo_backend.repository;

import com.marien.studi_jo_backend.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Integer> {


    Optional<Validation> findByCode(String code);
}
