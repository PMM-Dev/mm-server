package com.kwon770.mm.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTitleRepository extends JpaRepository<UserTitle, Long> {

    UserTitle findByTitle(Title title);
}
