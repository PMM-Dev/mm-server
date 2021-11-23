package com.kwon770.mm.domain.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findTopByOrderByCreatedDateDesc();

    List<Feedback> findAllByOrderByCreatedDateDesc();
}
