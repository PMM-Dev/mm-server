package com.kwon770.mm.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberTitleRepository extends JpaRepository<MemberTitle, Long> {

    Optional<MemberTitle> findByTitle(Title title);
}
