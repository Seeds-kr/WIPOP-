package com.example.movie.Entity;

import com.example.movie.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_table") //database에 해당 이름의 테이블 생성
//현재 사용 안함
@EntityListeners(AuditingEntityListener.class)
public class MovieEntity extends BaseTime{ //table 역할
    //jpa ==> database를 객체처럼 사용 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String movieName;

    @Column
    private double rating;

    //@ManyToMany
    //private Set<GenresEntity> genres;

    @Column(length = 500)
    private String body;

    @CreatedDate
    private LocalDateTime releaseDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        reviews.add(review);
        review.setMovie(this);
    }
    public MovieEntity toMovieData() {
        return MovieEntity.builder().id(this.id).movieName(this.movieName).rating(this.rating).build();
    }


    public static MovieEntity toMovieEntity(MovieDTO movieDTO){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieDTO.getId());
        movieEntity.setMovieName(movieDTO.getMovieName());
        movieEntity.setRating(movieDTO.getRating());
        return movieEntity;
    }

}
