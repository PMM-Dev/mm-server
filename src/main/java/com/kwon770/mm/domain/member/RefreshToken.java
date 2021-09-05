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
    private String tokenKey;

    @Column(nullable = false)
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshToken(String tokenKey, String value) {
        this.tokenKey = tokenKey;
        this.value = value;
    }
}
