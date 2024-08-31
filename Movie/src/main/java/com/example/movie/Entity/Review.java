package com.example.movie.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review extends BaseTime{

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String body;

    private String title;
    private Long score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieentity_id")
    private MovieInfoEntity movie;

    public Review(String title, String body, Member member, MovieInfoEntity movie, Long score) {
        this.title = title;
        this.body = body;
        this.member = member;
        this.movie = movie;
        this.score = score;
    }
}
