package com.marien.studi_jo_backend.repository;

import com.marien.studi_jo_backend.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
