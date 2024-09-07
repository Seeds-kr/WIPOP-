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

    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String memberId;
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
    @NotEmpty(message = "사용자명은 필수 입력 항목입니다.")
    private String userName;
    private String userMovieTag;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<MovieInfoEntity> movies = new ArrayList<>();

    public void addReview(Review review){
        reviews.add(review);
        review.setMember(this);
    }
}

