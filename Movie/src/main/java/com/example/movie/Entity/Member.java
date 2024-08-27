package com.example.movie.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member extends BaseTime {

    @Id @GeneratedValue
    private Long id;

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String userName;
    private String userMovieTag;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER) // EAGER로 설정
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER) // EAGER로 설정
    private List<MovieInfoEntity> movies = new ArrayList<>();

    public void addReview(Review review){
        reviews.add(review);
        review.setMember(this);
    }
}

