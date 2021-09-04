package com.kwon770.mm.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTitleRepository extends JpaRepository<MemberTitle, Long> {

    MemberTitle findByTitle(Title title);
}
