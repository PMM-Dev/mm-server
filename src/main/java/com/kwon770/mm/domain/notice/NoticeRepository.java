package com.kwon770.mm.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findTopByOrderByIdDesc();
}
