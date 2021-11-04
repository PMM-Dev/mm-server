package com.kwon770.mm.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String tokenKey;

    public RefreshToken updateValue(String token) {
        this.tokenKey = token;
        return this;
    }

    @Builder
    public RefreshToken(String memberId, String tokenKey) {
        this.memberId = memberId;
        this.tokenKey = tokenKey;
    }
}
