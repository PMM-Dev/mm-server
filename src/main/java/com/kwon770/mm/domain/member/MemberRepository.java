package com.kwon770.mm.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    boolean existsByEmail(String email);

    List<Member> findTop10ByNameContainingOrEmailContaining(String name, String email);

}
