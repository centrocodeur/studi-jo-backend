package com.marien.studi_jo_backend.repository;

import com.marien.studi_jo_backend.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
}
