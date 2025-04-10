package com.marien.studi_jo_backend.repository;

import com.marien.studi_jo_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    List<Review> findAllByTicketId(Long ticketsId);
}
