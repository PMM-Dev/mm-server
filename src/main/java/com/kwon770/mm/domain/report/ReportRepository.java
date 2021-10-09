package com.kwon770.mm.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findTopByOrderByCreatedDateDesc();

    List<Report> findAllByOrderByCreatedDateDesc();
}
