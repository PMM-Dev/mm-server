package com.kwon770.mm.domain.member;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.comment.Comment;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.dto.member.MemberRequestDto;
import com.kwon770.mm.dto.member.MemberUpdateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String encodedEmail;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Integer reviewCount = 0;

    @ManyToMany
    @JoinTable(
            name = "member_title_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "member_title_id")
    )
    private List<MemberTitle> titles = new ArrayList<>();



    /*
      Relation With Other Entities
     */
    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_post_like_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "member_restaurant_like_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> likedRestaurants = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_report_like_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    private List<Report> likedReports = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_comment_like_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<Comment> likedComments = new ArrayList<>();


    @Builder
    public Member(String name, String email, String encodedEmail, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.encodedEmail = encodedEmail;
        this.picture = picture;
        this.role = role;
    }

    public void update(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.email = memberRequestDto.getEmail();
        this.picture = memberRequestDto.getPicture();
        this.role = memberRequestDto.getRole();
    }

    public void update(MemberUpdateDto memberUpdateDto) {
        this.name = memberUpdateDto.getName();
        this.picture = memberUpdateDto.getPicture();
    }

    public void increaseReviewCount() {
        reviewCount++;
    }

    public void decreaseReviewCount() {
        reviewCount--;
    }

    public void appendTitle(MemberTitle memberTitle) { this.titles.add(memberTitle); }

    public void subtractTitle(MemberTitle memberTitle) { this.titles.remove(memberTitle); }

    public void appendLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.add(restaurant); }

    public void subtractedLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.remove(restaurant); }

    public void appendLikedReport(Report report) { this.likedReports.add(report); }

    public void subtractedLikedReport(Report report) { this.likedReports.remove(report); }

    public void appendLikedPost(Post post) { this.likedPosts.add(post); }

    public void subtractedLikedPost(Post post) { this.likedPosts.remove(post); }

    public void appendLikedComment(Comment comment) { this.likedComments.add(comment); }

    public void subtractedLikedComment(Comment comment) { this.likedComments.remove(comment); }
}
