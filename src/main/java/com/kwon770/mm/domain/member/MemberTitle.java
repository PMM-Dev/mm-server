package com.kwon770.mm.domain.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class MemberTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Title title;

    @ManyToMany(mappedBy = "titles")
    @JsonBackReference
    private List<Member> member = new ArrayList<>();

    @Builder
    public MemberTitle(Title title) { this.title = title;}
}
