package com.kwon770.mm.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class UserTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Title title;

    @ManyToMany(mappedBy = "titles")
    @JsonBackReference
    private List<User> user = new ArrayList<>();

    @Builder
    public UserTitle(Title title) { this.title = title;}
}
