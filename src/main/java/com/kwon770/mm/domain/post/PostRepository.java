package com.kwon770.mm.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findTop3ByOrderByCreatedDateDesc();
}
