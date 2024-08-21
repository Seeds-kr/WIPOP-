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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieentity_id")
    private MovieEntity movie;

    public Review(String body, Member member, MovieEntity movie, Long score) {
        this.body = body;
        this.member = member;
        this.movie = movie;
        this.score = score;
    }
}
